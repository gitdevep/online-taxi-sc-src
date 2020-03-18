package com.online.taxi.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.taxi.constatnt.*;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.phone.BoundPhoneDto;
import com.online.taxi.dto.valuation.PriceResult;
import com.online.taxi.dto.valuation.charging.KeyRule;
import com.online.taxi.dto.valuation.charging.Rule;
import com.online.taxi.dto.valuation.charging.TagPrice;
import com.online.taxi.entity.*;
import com.online.taxi.util.EncriptUtil;
import com.online.taxi.util.RestTemplateHepler;
import com.online.taxi.dto.*;
import com.online.taxi.mapper.*;
import com.online.taxi.request.OrderDtoRequest;
import com.online.taxi.service.OrderService;
import com.online.taxi.task.OrderRequestTask;
import com.online.taxi.task.OtherInterfaceTask;
import com.online.taxi.utils.IdWorker;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 订单服务
 *
 * @date 2018/8/25
 */
@Slf4j
@Repository
public class OrderServiceImpl implements OrderService {

    private static final String PASSENGERS_IS_NULL = "乘客信息为空";
    private static final String VALUATION_RULES_CHANGE = "当前预估价格已变化";

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private PassengerInfoMapper passengerInfoMapper;

    @Autowired
    private OrderRulePriceMapper orderRulePriceMapper;

    @Autowired
    private OrderRuleMirrorMapper orderRuleMirrorMapper;

    @Autowired
    private DriverInfoMapper driverInfoMapper;

    @Autowired
    private CarInfoMapper carInfoMapper;

    @Autowired
    private OtherInterfaceTask otherInterfaceTask;

    @Autowired
    private OrderRequestTask orderRequestTask;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private boolean flag;
    private boolean sate;

    /**
     * 订单预估
     *
     * @param orderDtoRequest
     * @return
     * @throws Exception
     */
    @Override
    public ResponseResult<OrderPrice> forecastOrderCreate(OrderDtoRequest orderDtoRequest) throws Exception {
        log.info("orderDtoRequest={}", orderDtoRequest);
        OrderPrice orderPrice = new OrderPrice();
        ResponseResult responseResult;
        if (null == orderDtoRequest.getOrderId()) {
            if (OrderServiceTypeEnum.CHARTERED_CAR.getCode() != orderDtoRequest.getServiceTypeId() && OrderServiceTypeEnum.THROUGHOUT_THE_DAY.getCode() != orderDtoRequest.getServiceTypeId()) {
                try {
                    responseResult = otherInterfaceTask.requestRoute(orderDtoRequest);
                    if (BusinessInterfaceStatus.SUCCESS.getCode() != responseResult.getCode()) {
                        return responseResult;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("测量距离失败！");
                    throw e;
                }
            }
            responseResult = orderRequestTask.checkBaseInfo(orderDtoRequest);
            if (BusinessInterfaceStatus.SUCCESS.getCode() != responseResult.getCode()) {
                return responseResult;
            }
            try {
                BaseInfoDto baseInfoDto = RestTemplateHepler.parse(responseResult, BaseInfoDto.class);
                orderDtoRequest.setCityName(baseInfoDto.getCityName());
                orderDtoRequest.setChannelName(baseInfoDto.getChannelName());
                orderDtoRequest.setServiceTypeName(baseInfoDto.getServiceTypeName());
                orderDtoRequest.setCarLevelName(baseInfoDto.getCarLevelName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            responseResult = otherInterfaceTask.getOrderChargeRule(orderDtoRequest);
            if (BusinessInterfaceStatus.SUCCESS.getCode() != responseResult.getCode()) {
                return responseResult;
            }
            Rule rule = RestTemplateHepler.parse(responseResult, Rule.class);
            log.info("Rule1{}"+rule);
            try {
                responseResult = createOrderAndOrderRuleMirror(orderDtoRequest, rule);
                if (BusinessInterfaceStatus.SUCCESS.getCode() != responseResult.getCode()) {
                    return responseResult;
                }
                Object data = responseResult.getData();
                orderDtoRequest.setOrderId(Integer.valueOf(data.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (null != orderDtoRequest.getUserFeature()) {
                responseResult = updateOrderServiceAndUserFeature(orderDtoRequest);
                if (BusinessInterfaceStatus.SUCCESS.getCode() != responseResult.getCode()) {
                    return responseResult;
                }
            }
            responseResult = updateOrderRuleMirror(orderDtoRequest);
            if (BusinessInterfaceStatus.SUCCESS.getCode() == responseResult.getCode()) {
                sate = true;
            } else {
                return responseResult;
            }
        }
        if (sate) {
            try {
                PriceResult priceResult = otherInterfaceTask.getOrderPrice(orderDtoRequest.getOrderId());
                orderPrice.setPrice(priceResult.getPrice());
            } catch (Exception e) {
                e.printStackTrace();
                log.error("计价接口发生错误", e);
                throw e;
            }
        }
        orderPrice.setOrderId(orderDtoRequest.getOrderId());
        return ResponseResult.success(orderPrice);
    }

    /**
     * 创建订单和计价规则
     *
     * @param orderDtoRequest
     * @return
     * @throws Exception
     */
    public ResponseResult createOrderAndOrderRuleMirror(OrderDtoRequest orderDtoRequest, Rule rule) throws Exception {
        ResponseResult responseResult;
        Integer orderId;
        if (OrderServiceTypeEnum.CHARTERED_CAR.getCode() == orderDtoRequest.getServiceTypeId() || OrderServiceTypeEnum.THROUGHOUT_THE_DAY.getCode() == orderDtoRequest.getServiceTypeId()) {
            orderDtoRequest.setEndAddress("-");
            orderDtoRequest.setEndLongitude(orderDtoRequest.getStartLongitude());
            orderDtoRequest.setEndLatitude(orderDtoRequest.getStartLatitude());
        }
        //创建订单
        responseResult = createOrder(orderDtoRequest);
        if (BusinessInterfaceStatus.SUCCESS.getCode() == responseResult.getCode()) {
            flag = true;
            Order order = RestTemplateHepler.parse(responseResult, Order.class);
            orderId = order.getId();
            responseResult = insertOrUpdateOrderRuleMirror(rule, orderId);
            if (BusinessInterfaceStatus.SUCCESS.getCode() != responseResult.getCode()) {
                return responseResult;
            }
            sate = true;
            BoundValueOperations<String, String> ops = redisTemplate.boundValueOps(String.format("%s:%s:%s", OrderRuleNames.PREFIX, OrderRuleNames.RULE, orderId));
            ops.set(new ObjectMapper().writeValueAsString(rule), 1, TimeUnit.HOURS);
        } else {
            return responseResult;
        }
        return responseResult.setData(orderId);
    }

    /**
     * 创建订单
     *
     * @param orderDtoRequest
     * @return
     */
    private ResponseResult createOrder(OrderDtoRequest orderDtoRequest) {
        if (!StringUtils.isEmpty(orderDtoRequest.getPassengerInfoId().toString())) {
            PassengerInfo passengerInfo = passengerInfoMapper.selectByPrimaryKey(orderDtoRequest.getPassengerInfoId());
            if (passengerInfo == null) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), PASSENGERS_IS_NULL);
            }
            String orderNumber = idWorker.nextId();
            Order order = new Order();
            order.setOrderNumber(orderNumber);
            order.setPassengerInfoId(orderDtoRequest.getPassengerInfoId());
            order.setPassengerPhone(passengerInfo.getPhone());
            order.setDeviceCode(orderDtoRequest.getDeviceCode());
            order.setUserLongitude(orderDtoRequest.getUserLongitude());
            order.setUserLatitude(orderDtoRequest.getUserLatitude());
            order.setStartLongitude(orderDtoRequest.getStartLongitude());
            order.setStartLatitude(orderDtoRequest.getStartLatitude());
            order.setStartAddress(orderDtoRequest.getStartAddress());
            order.setServiceType(orderDtoRequest.getServiceTypeId());
            order.setStatus(OrderStatusEnum.CALL_ORDER_FORECAST.getCode());
            Date startDate = new Date();
            if (OrderEnum.SERVICE_TYPE.getCode() != orderDtoRequest.getServiceTypeId()) {
                startDate = orderDtoRequest.getOrderStartTime();
            }
            order.setOrderStartTime(startDate);
            order.setOrderChannel(orderDtoRequest.getChannelId());
            order.setServiceType(orderDtoRequest.getServiceTypeId());
            order.setEndLongitude(orderDtoRequest.getEndLongitude());
            order.setEndLatitude(orderDtoRequest.getEndLatitude());
            order.setEndAddress(orderDtoRequest.getEndAddress());
            order.setSource(orderDtoRequest.getSource());
            order.setCreateTime(new Date());
            if (null != orderDtoRequest.getUserFeature()) {
                order.setUserFeature(orderDtoRequest.getUserFeature());
            }
            orderMapper.insertSelective(order);
            return ResponseResult.success(order);
        } else {
            log.error(PASSENGERS_IS_NULL + "乘客手机号：" + orderDtoRequest.getPassengerPhone());
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), PASSENGERS_IS_NULL);
        }
    }

    /**
     * 叫车
     *
     * @param orderDtoRequest
     * @return
     */
    @Override
    public ResponseResult callCar(OrderDtoRequest orderDtoRequest) throws Exception {
        ResponseResult responseResult;
        log.info("OrderRequest={}", orderDtoRequest);
        try {
            Integer orderId = orderDtoRequest.getOrderId();
            if (StringUtils.isEmpty(orderId)) {
                log.error("订单Id为空");
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "订单Id为空");
            }
            Order order = orderMapper.selectByPrimaryKey(orderId);

            Rule oldRule = parse(orderRuleMirrorMapper.selectByPrimaryKey(orderId).getRule(), Rule.class);
            KeyRule keyRule = oldRule.getKeyRule();
            orderDtoRequest.setCarLevelId(keyRule.getCarLevelId());
            orderDtoRequest.setCarLevelName(keyRule.getCarLevelName());
            orderDtoRequest.setServiceTypeId(keyRule.getServiceTypeId());
            orderDtoRequest.setServiceTypeName(keyRule.getServiceTypeName());
            orderDtoRequest.setChannelId(keyRule.getChannelId());
            orderDtoRequest.setChannelName(keyRule.getChannelName());
            orderDtoRequest.setCityCode(keyRule.getCityCode());
            orderDtoRequest.setCityName(keyRule.getCityName());
            orderDtoRequest.setUserFeature(order.getUserFeature());

            responseResult = otherInterfaceTask.getOrderChargeRule(orderDtoRequest);
            if (BusinessInterfaceStatus.SUCCESS.getCode() != responseResult.getCode()) {
                return responseResult;
            }
            Rule newRule = RestTemplateHepler.parse(responseResult, Rule.class);
            flag = false;
            responseResult = insertOrUpdateOrderRuleMirror(newRule, orderId);
            if (BusinessInterfaceStatus.SUCCESS.getCode() != responseResult.getCode()) {
                return responseResult;
            }
            if (!isSameRule(oldRule, newRule)) {
                log.error("计价规则有变，originalRule={}，newestRule={}", oldRule, newRule);
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), VALUATION_RULES_CHANGE);
            }
            order.setStartTime(new Date());
            if (OrderEnum.SERVICE_TYPE.getCode() == orderDtoRequest.getServiceTypeId()) {
                order.setOrderStartTime(new Date());
            }
            order.setStatus(OrderStatusEnum.STATUS_ORDER_START.getCode());
            order.setOrderType(orderDtoRequest.getOrderType());

            String otherPhone;
            if (orderDtoRequest.getOrderType() == OrderEnum.ORDER_TYPE_OTHER.getCode()) {
                if (StringUtils.isEmpty(orderDtoRequest.getOtherPhone())) {
                    log.error("给它人叫车，手机号为空！");
                    return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "给它人叫车，手机号不能为空！");
                }
                otherPhone = EncriptUtil.encryptionPhoneNumber(orderDtoRequest.getOtherPhone());
                order.setOtherName(orderDtoRequest.getOtherName());
            } else {
                PassengerInfo passengerInfo = passengerInfoMapper.selectByPrimaryKey(order.getPassengerInfoId());
                otherPhone = passengerInfo.getPhone();
                order.setOtherName(passengerInfo.getPassengerName());
            }
            order.setOtherPhone(otherPhone);
            orderMapper.updateByPrimaryKeySelective(order);

            BoundValueOperations<String, String> ops = redisTemplate.boundValueOps(String.format("%s:%s:%s", OrderRuleNames.PREFIX, OrderRuleNames.RULE, orderId));
            ops.set(new ObjectMapper().writeValueAsString(newRule));

            responseResult = otherInterfaceTask.donePrice(orderId);
            if (BusinessInterfaceStatus.SUCCESS.getCode() != responseResult.getCode()) {
                return responseResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return ResponseResult.success();
    }

    /**
     * 修改订单服务类型与标签
     *
     * @param orderDtoRequest
     * @return
     */
    public ResponseResult updateOrderServiceAndUserFeature(OrderDtoRequest orderDtoRequest) {
        log.info("OrderRequest={}", orderDtoRequest);
        int i;
        Order newOrder = orderMapper.selectByPrimaryKey(orderDtoRequest.getOrderId());
        newOrder.setUserFeature(orderDtoRequest.getUserFeature());
        i = orderMapper.updateByPrimaryKeySelective(newOrder);
        if (i == 0) {
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "更新失败");
        }
        return ResponseResult.success();
    }

    /**
     * 订单修改
     *
     * @param orderDtoRequest
     * @return
     * @throws Exception
     */
    @Override
    public ResponseResult updateOrder(OrderDtoRequest orderDtoRequest, String driverLongitude, String driverLatitude) {
        ResponseResult responseResult;
        int up = 0;
        Order order = new Order();
        BeanUtils.copyProperties(orderDtoRequest, order);
        order.setId(orderDtoRequest.getOrderId());
        JSONObject json = JSONObject.fromObject(order);
        log.info("Order={}", json.toString());
        try {
            if (order.getId() == null) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "订单id为空");
            }
            try {
                if (order.getStatus() != null) {
                    int status = order.getStatus().intValue();
                    System.out.println(status);
                    if (OrderStatusEnum.STATUS_RESERVED_ORDER_TO_PICK_UP.getCode() == status) {
                        order.setDriverStartTime(new Date());
                    }
                    if (OrderStatusEnum.STATUS_DRIVER_ARRIVED.getCode() == status) {
                        order.setDriverArrivedTime(new Date());
                    }
                    if (OrderStatusEnum.STATUS_DRIVER_TRAVEL_START.getCode() == status) {
                        order.setReceivePassengerTime(new Date());
                    }
                    if (OrderStatusEnum.STATUS_DRIVER_TRAVEL_END.getCode() == order.getStatus()) {
                        order.setPassengerGetoffTime(new Date());
                    }
                }
                if (null != orderDtoRequest.getUpdateType()) {
                    up = orderMapper.updateByPrimaryKey(orderDtoRequest);
                } else {
                    log.info("order={}"+order);
                    up = orderMapper.updateByPrimaryKeySelective(order);
                }
            } catch (Exception e) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "更新失败");
            }
            Order newOrder = orderMapper.selectByPrimaryKey(order.getId());
            if (order.getStatus() != null || order.getIsCancel() != null) {
                //手机号解绑
                responseResult = orderRequestTask.phoneUnbindAndInsertOrderPoint(order.getStatus(), newOrder, order.getIsCancel());
                log.info("responseResult={}", responseResult);
            }
            if (StringUtils.isEmpty(driverLongitude) && StringUtils.isEmpty(driverLatitude)) {
                responseResult = updateMapInterface(newOrder, driverLongitude, driverLatitude);
                if (BusinessInterfaceStatus.SUCCESS.getCode() != responseResult.getCode()) {
                    return responseResult;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (up == 0) {
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "更新失败");
        }
        return ResponseResult.success();
    }

    /**
     * 更新订单地图
     *
     * @param order
     * @return
     */
    public ResponseResult updateMapInterface(Order order, String driverLongitude, String driverLatitude) {
        ResponseResult responseResult;
        Boolean status = false;
        //乘客上车，司机开始行程
        if (OrderStatusEnum.STATUS_DRIVER_TRAVEL_START.getCode() == order.getStatus().intValue()) {
            status = true;
        }
        //订单取消
        if (order.getIsCancel() != null && order.getIsCancel() == OrderEnum.IS_CANCEL.getCode() && OrderStatusEnum.STATUS_PAY_START.getCode() > order.getStatus()) {
            status = true;
        }
        //发起收款,未支付
        if (OrderStatusEnum.STATUS_PAY_START.getCode() == order.getStatus() && OrderEnum.NOT_PAY.getCode() == order.getIsPaid()) {
            status = true;
        }
        //订单已评价
        if (OrderEnum.IS_PAY.getCode() == order.getIsPaid() && OrderEnum.IS_EVALUATE.getCode() == order.getIsEvaluate() && OrderStatusEnum.STATUS_PAY_END.getCode() == order.getStatus()) {
            status = true;
        }
        //到达目的地，行程结束，未支付
        if (OrderStatusEnum.STATUS_DRIVER_TRAVEL_END.getCode() == order.getStatus() && OrderEnum.NOT_PAY.getCode() == order.getIsPaid()) {
            status = true;
        }

        //支付完成,未评价
        if (OrderStatusEnum.STATUS_PAY_END.getCode() == order.getStatus() && OrderEnum.IS_PAY.getCode() == order.getIsPaid() && OrderEnum.NOT_EVALUATE.getCode() == order.getIsEvaluate()) {
            status = true;
        }

        //取消订单未支付
        if (order.getIsCancel() != null && order.getIsCancel() == OrderEnum.IS_CANCEL.getCode() && OrderStatusEnum.STATUS_PAY_START.getCode() == order.getStatus()) {
            status = true;
        }
        if (status) {
            OrderRuleMirror orderRuleMirror = orderRuleMirrorMapper.selectByPrimaryKey(order.getId());
            String originalRule = orderRuleMirror.getRule();
            Rule rule = parse(originalRule, Rule.class);

            KeyRule keyRule = rule.getKeyRule();
            responseResult = otherInterfaceTask.updateMap(order, Integer.valueOf(keyRule.getCityCode()), keyRule.getCarLevelId(), driverLongitude, driverLatitude);
            if (BusinessInterfaceStatus.SUCCESS.getCode() != responseResult.getCode()) {
                return responseResult;
            }
        }
        return ResponseResult.success(null);
    }

    /**
     * 订单其它费用结算
     *
     * @param orderDtoRequest
     * @return
     * @throws Exception
     */
    @Override
    public ResponseResult<OrderOtherPrice> otherPriceBalance(OrderDtoRequest orderDtoRequest) throws Exception {
        log.info("OrderRequest{}", orderDtoRequest);
        Order orderRequest = new Order();
        BeanUtils.copyProperties(orderDtoRequest, JSONObject.fromObject(orderRequest).toString());
        log.info("orderRequest={}", orderRequest);
        OrderOtherPrice orderOtherPrice = new OrderOtherPrice();
        try {
            OrderRulePrice orderRulePrice = orderRulePriceMapper.selectByPrimaryKey(orderDtoRequest.getOrderId(), ChargingCategoryEnum.Settlement.getCode());
            BigDecimal totalPrice = orderRulePrice.getTotalPrice();
            if (StringUtils.isEmpty(orderDtoRequest.getOrderId())) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "orderId为空");
            }
            if (!ObjectUtils.nullSafeEquals(orderDtoRequest.getRoadPrice(), 0)) {
                totalPrice = totalPrice.add(orderDtoRequest.getRoadPrice());
                orderRulePrice.setRoadPrice(orderDtoRequest.getRoadPrice());
            }
            if (!ObjectUtils.nullSafeEquals(orderDtoRequest.getParkingPrice(), 0)) {
                totalPrice = totalPrice.add(orderDtoRequest.getParkingPrice());
                orderRulePrice.setParkingPrice(orderDtoRequest.getParkingPrice());
            }
            if (!ObjectUtils.nullSafeEquals(orderDtoRequest.getOtherPrice(), 0)) {
                totalPrice = totalPrice.add(orderDtoRequest.getOtherPrice());
                orderRulePrice.setOtherPrice(orderDtoRequest.getOtherPrice());
            }
            orderRulePrice.setTotalPrice(totalPrice);
            int updateSize = orderRulePriceMapper.updateByPrimaryKeySelective(orderRulePrice);
            if (updateSize == 0) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "更新价格失败");
            } else {
                Order order = orderMapper.selectByPrimaryKey(orderDtoRequest.getOrderId());
                order.setStatus(orderDtoRequest.getStatus());
                int up1 = orderMapper.updateByPrimaryKeySelective(order);
                if (up1 == 0) {
                    return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "更新订单失败");
                }
                orderOtherPrice.setOrderId(order.getId());
                orderOtherPrice.setPassengerId(order.getPassengerInfoId());
                //if (totalPrice.compareTo(orderRulePrice.getLowestPrice()) == 1 || totalPrice.compareTo(orderRulePrice.getLowestPrice()) == 0) {
                orderOtherPrice.setTotalPrice(totalPrice);
                //} else {
                //    orderOtherPrice.setTotalPrice(orderRulePrice.getLowestPrice());
                //}
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return ResponseResult.success(orderOtherPrice);
    }

    /**
     * 订单改派
     *
     * @param orderDtoRequest
     * @return
     */
    @Override
    public ResponseResult reassignmentOrder(OrderDtoRequest orderDtoRequest) {
        log.info("OrderRequest{}", orderDtoRequest);
        try {
            DriverInfo driverInfo = driverInfoMapper.selectByPrimaryKey(orderDtoRequest.getDriverIdNow());
            if (null == driverInfo) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "司机不存在");
            }
            Order order = orderMapper.selectByPrimaryKey(orderDtoRequest.getOrderId());
            CarInfo carInfo = carInfoMapper.selectByPrimaryKey(driverInfo.getCarId());
            if (null == carInfo) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "车辆不存在");
            }
            if (null == order.getDriverId()) {
                order.setIsManual(OrderEnum.ORDER_IS_MANUAL_FLAG1.getCode());
            } else {
                order.setIsManual(OrderEnum.ORDER_IS_MANUAL_FLAG2.getCode());
            }
            order.setDriverId(orderDtoRequest.getDriverIdNow());
            order.setDriverPhone(driverInfo.getPhoneNumber());
            order.setCarId(driverInfo.getCarId());
            order.setPlateNumber(carInfo.getPlateNumber());
            //手机号解绑
            if (order.getMappingNumber() != null) {
                try {
                    otherInterfaceTask.phoneNumberUnbind(order.getMappingId(), order.getMappingNumber());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //它人手机号解绑
            if (order.getOtherMappingNumber() != null) {
                try {
                    otherInterfaceTask.phoneNumberUnbind(order.getOtherMappingId(), order.getOtherMappingNumber());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //手机号绑定
            String driverPhone = EncriptUtil.decryptionPhoneNumber(driverInfo.getPhoneNumber());
            String passengerPhone = EncriptUtil.decryptionPhoneNumber(order.getPassengerPhone());

            ResponseResult responseResult = otherInterfaceTask.phoneNumberBind(order.getOrderStartTime(), driverPhone, passengerPhone);
            if (responseResult.getCode() == BusinessInterfaceStatus.SUCCESS.getCode()) {
                BoundPhoneDto boundPhoneDto = RestTemplateHepler.parse(responseResult, BoundPhoneDto.class);
                order.setMappingId(boundPhoneDto.getAxbSubsId());
                order.setMappingNumber(boundPhoneDto.getAxbSecretNo());
            }
            if (order.getOrderType() == OrderEnum.ORDER_TYPE_OTHER.getCode()) {
                String otherPhone = EncriptUtil.decryptionPhoneNumber(order.getOtherPhone());
                ResponseResult otherResponseResult = otherInterfaceTask.phoneNumberBind(order.getOrderStartTime(), driverPhone, otherPhone);
                if (otherResponseResult.getCode() == BusinessInterfaceStatus.SUCCESS.getCode()) {
                    BoundPhoneDto boundPhoneDto = RestTemplateHepler.parse(otherResponseResult, BoundPhoneDto.class);
                    order.setOtherMappingId(boundPhoneDto.getAxbSubsId());
                    order.setOtherMappingNumber(boundPhoneDto.getAxbSecretNo());
                }
            }
            order.setStatus(OrderStatusEnum.STATUS_DRIVER_ACCEPT.getCode());
            orderMapper.updateByPrimaryKeySelective(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseResult.success();
    }

    /**
     * 批量修改
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ResponseResult batchUpdate(OrderDtoRequest request) {
        log.info("orderIds{}", request);
        if (request.getInvoiceType() <= 0) {
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "状态不能小于等于0");
        }
        if (request.getOrderIds().length() == 0) {
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "OrderId数量不能为空");
        }
        try {
            Map<String, Object> map = new HashMap<>(2);
            map.put("orderIds", request.getOrderIds());
            map.put("invoiceType", request.getInvoiceType());
            orderMapper.batchUpdate(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "修改失败");
        }
        return ResponseResult.success();
    }

    /**
     * 修改计价规则
     *
     * @param orderDtoRequest
     * @return
     */
    public ResponseResult updateOrderRuleMirror(OrderDtoRequest orderDtoRequest) throws Exception {
        ResponseResult responseResult;
        OrderRuleMirror orderRuleMirror = orderRuleMirrorMapper.selectByPrimaryKey(orderDtoRequest.getOrderId());
        String originalRule = orderRuleMirror.getRule();
        Rule rule = parse(originalRule, Rule.class);
        KeyRule keyRule = rule.getKeyRule();

        OrderKeyRuleDto orderKeyRuleDto = new OrderKeyRuleDto();
        orderKeyRuleDto.setOrderId(orderDtoRequest.getOrderId());
        orderDtoRequest.setServiceTypeId(keyRule.getServiceTypeId());
        orderDtoRequest.setServiceTypeName(keyRule.getServiceTypeName());
        orderDtoRequest.setChannelId(keyRule.getChannelId());
        orderDtoRequest.setChannelName(keyRule.getChannelName());
        orderDtoRequest.setCityCode(keyRule.getCityCode());
        orderDtoRequest.setCityName(keyRule.getCityName());

        responseResult = otherInterfaceTask.getOrderChargeRule(orderDtoRequest);
        if (BusinessInterfaceStatus.SUCCESS.getCode() != responseResult.getCode()) {
            return responseResult;
        }
        Rule newRule = null;
        try {
            newRule = RestTemplateHepler.parse(responseResult, Rule.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        flag = false;
        responseResult = insertOrUpdateOrderRuleMirror(newRule, orderDtoRequest.getOrderId());
        if (BusinessInterfaceStatus.SUCCESS.getCode() != responseResult.getCode()) {
            return responseResult;
        }
        BoundValueOperations<String, String> ops = redisTemplate.boundValueOps(String.format("%s:%s:%s", OrderRuleNames.PREFIX, OrderRuleNames.RULE, orderDtoRequest.getOrderId()));
        ops.set(new ObjectMapper().writeValueAsString(newRule), 1, TimeUnit.HOURS);
        return ResponseResult.success();
    }

    /**
     * 插入更新计价规则
     *
     * @param orderId
     * @return
     */
    public ResponseResult insertOrUpdateOrderRuleMirror(Rule rule, Integer orderId) {
        log.info("Rule{}"+rule);
        OrderRuleMirror orderRuleMirror = new OrderRuleMirror();
        try {
            orderRuleMirror.setOrderId(orderId);
            orderRuleMirror.setRuleId(rule.getId());
            orderRuleMirror.setRule(new ObjectMapper().writeValueAsString(rule));
            int up = 0;
            if (!flag) {
                up = orderRuleMirrorMapper.updateByPrimaryKeySelective(orderRuleMirror);
            } else {
                orderRuleMirror.setCreateTime(new Date());
                up = orderRuleMirrorMapper.insertSelective(orderRuleMirror);
            }
            if (up == 0) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "更新计价规则失败");
            }
        } catch (JsonProcessingException e) {
            log.error("插入或更新计价规则失败");
            e.printStackTrace();
        }
        return ResponseResult.success();
    }

    /**
     * 字符串转实体类
     *
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    private static <T> T parse(String jsonStr, Class<T> clazz) {
        ObjectMapper om = new ObjectMapper();
        T readValue = null;
        try {
            readValue = om.readValue(jsonStr, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readValue;
    }

    /**
     * 判断计价规则是否一致
     *
     * @param oldRule 计价规则1
     * @param newRule 计价规则2
     * @return true：一致，否则false
     */
    @SneakyThrows
    private boolean isSameRule(Rule oldRule, Rule newRule) {
        Rule oldRuleClone = new Rule();
        BeanUtils.copyProperties(oldRule, oldRuleClone);
        oldRuleClone.setTagPrices(null);

        Rule newRuleClone = new Rule();
        BeanUtils.copyProperties(newRule, newRuleClone);
        newRuleClone.setTagPrices(null);

        //非标签计价比较
        ObjectMapper mapper = new ObjectMapper();
        if (!ObjectUtils.nullSafeEquals(mapper.writeValueAsString(newRuleClone), mapper.writeValueAsString(oldRuleClone))) {
            return false;
        }

        //标签计价比较
        List<TagPrice> oldRuleTagPrices = Optional.ofNullable(oldRule.getTagPrices()).orElse(new ArrayList<>());
        List<TagPrice> newRuleTagPrices = Optional.ofNullable(newRule.getTagPrices()).orElse(new ArrayList<>());
        for (TagPrice tagPrice : oldRuleTagPrices) {
            if (newRuleTagPrices.stream().anyMatch(p -> p.getName().equals(tagPrice.getName()) && p.getPrice().compareTo(tagPrice.getPrice()) != 0)) {
                return false;
            }
        }
        return true;
    }
}
