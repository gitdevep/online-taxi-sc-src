package com.online.taxi.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.taxi.constatnt.BusinessInterfaceStatus;
import com.online.taxi.constatnt.OrderEnum;
import com.online.taxi.constatnt.OrderStatusEnum;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.phone.BoundPhoneDto;
import com.online.taxi.dto.valuation.charging.KeyRule;
import com.online.taxi.dto.valuation.charging.Rule;
import com.online.taxi.entity.CarInfo;
import com.online.taxi.entity.DriverInfo;
import com.online.taxi.entity.Order;
import com.online.taxi.entity.OrderRuleMirror;
import com.online.taxi.util.EncriptUtil;
import com.online.taxi.util.RestTemplateHepler;
import com.online.taxi.consts.DriverInfoConst;
import com.online.taxi.mapper.CarInfoMapper;
import com.online.taxi.mapper.DriverInfoMapper;
import com.online.taxi.mapper.OrderMapper;
import com.online.taxi.mapper.OrderRuleMirrorMapper;
import com.online.taxi.request.OrderDtoRequest;
import com.online.taxi.service.OrderGrabService;
import com.online.taxi.task.OrderRequestTask;
import com.online.taxi.task.OtherInterfaceTask;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述
 *
 * @date 2018/8/25
 */
@Service
@Slf4j
public class OrderGrabServiceImpl implements OrderGrabService {

    private static final String DRIVER_IS_NULL = "司机信息不存在";
    private static final String DRIVER_OUT_OF_SERVICE = "司机停用";
    private static final String ORDER_ROBBED = "订单已经被抢";
    private static final String GRAB_FAILURE = "抢单失败";
    private static final String DRIVER_WORK_STATUS_NOT_DISPATCH_VEHICLE = "司机工作状态不是出车";

    private String lockKey = "lock_order:";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DriverInfoMapper driverInfoMapper;

    @Autowired
    private OrderRuleMirrorMapper orderRuleMirrorMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CarInfoMapper carInfoMapper;

    @Autowired
    private OtherInterfaceTask otherInterfaceTask;

    @Override
    public ResponseResult grab(OrderDtoRequest orderDtoRequest) {
        Order orderRequest = new Order();
        BeanUtils.copyProperties(orderDtoRequest, orderRequest);
        log.info("orderRequest={}", JSONObject.fromObject(orderRequest).toString());
        Integer orderId = orderDtoRequest.getOrderId();
        Integer driverId = orderDtoRequest.getDriverId();

        if (null != orderId && orderId != 0 && null != driverId && driverId != 0) {
            DriverInfo driverInfo = driverInfoMapper.selectByPrimaryKey(driverId);
            if (null == driverInfo) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), DRIVER_IS_NULL);
            }
            OrderRuleMirror orderRuleMirror = orderRuleMirrorMapper.selectByPrimaryKey(orderId);
            String originalRule = orderRuleMirror.getRule();
            Rule rule = parse(originalRule, Rule.class);
            KeyRule keyRule = rule.getKeyRule();
            Integer cityCode = Integer.parseInt(keyRule.getCityCode());
            Integer carType = keyRule.getCarLevelId();

            Order order = orderMapper.selectByPrimaryKey(orderId);
            int orderStatusInt = order.getStatus();
            if (orderStatusInt != OrderStatusEnum.STATUS_ORDER_START.getCode()) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), ORDER_ROBBED);
            }
            Integer useStatus = driverInfo.getUseStatus();
            if (null != useStatus && useStatus.intValue() == 0) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), DRIVER_OUT_OF_SERVICE);
            }
            String driverPhone = driverInfo.getPhoneNumber();

            ResponseResult responseResult = orderStatusProcessing(orderId, driverInfo.getWorkStatus(), driverId, driverInfo.getCarId(), driverPhone, order.getOrderStartTime(), cityCode, carType, orderDtoRequest);
            if (responseResult.getCode() == BusinessInterfaceStatus.SUCCESS.getCode()) {
                return ResponseResult.success();
            } else {
                return responseResult;
            }
        }
        return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), DRIVER_WORK_STATUS_NOT_DISPATCH_VEHICLE);
    }

    /**
     * 订单状态处理
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult orderStatusProcessing(Integer orderId, Integer workStatus, Integer driverId, Integer carId, String driverPhone, Date startTime, Integer cityCode, Integer carType, OrderDtoRequest orderDtoRequest) {
        ResponseResult responseResult;
        JSONObject json = new JSONObject();
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (null != workStatus && workStatus.intValue() == DriverInfoConst.WORK_START) {
            String lock = (lockKey + orderId).intern();
            String uuid = UUID.randomUUID().toString();
            BoundValueOperations<String, String> lockRedis;
            lockRedis = redisTemplate.boundValueOps(lock);
            Boolean lockBoolean = lockRedis.setIfAbsent(uuid);
            if (lockBoolean) {
                lockRedis.expire(15L, TimeUnit.SECONDS);
            } else {
                log.error(GRAB_FAILURE);
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), GRAB_FAILURE);
            }

            CarInfo carInfo = carInfoMapper.selectByPrimaryKey(carId);
            Integer status = OrderStatusEnum.STATUS_DRIVER_ACCEPT.getCode();
            order.setDriverId(driverId);
            order.setDriverPhone(driverPhone);
            order.setStatus(status);
            order.setCarId(carId);
            order.setPlateNumber(carInfo.getPlateNumber());
            order.setDriverStatus(orderDtoRequest.getDriverStatus());

            String passengerPhone = order.getPassengerPhone();
            driverPhone = EncriptUtil.decryptionPhoneNumber(driverPhone);
            passengerPhone = EncriptUtil.decryptionPhoneNumber(passengerPhone);

            if (order.getOrderType() == OrderEnum.ORDER_TYPE_OTHER.getCode()) {
                String otherPhone = EncriptUtil.decryptionPhoneNumber(order.getOtherPhone());
                log.info("它人手机号：==="+otherPhone);
                responseResult = otherInterfaceTask.phoneNumberBind(startTime, driverPhone, otherPhone);
                log.info("responseResult=="+responseResult);
                if (responseResult.getCode() == BusinessInterfaceStatus.SUCCESS.getCode()) {
                    try {
                        BoundPhoneDto boundPhoneDto = RestTemplateHepler.parse(responseResult, BoundPhoneDto.class);
                        order.setOtherMappingId(boundPhoneDto.getAxbSubsId());
                        order.setOtherMappingNumber(boundPhoneDto.getAxbSecretNo());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            log.info("司机手机号是：" + driverPhone);
            log.info("乘客手机号是：" + passengerPhone);
            //手机号绑定
            responseResult = otherInterfaceTask.phoneNumberBind(startTime, driverPhone, passengerPhone);
            if (responseResult.getCode() == BusinessInterfaceStatus.SUCCESS.getCode()) {
                try {
                    BoundPhoneDto boundPhoneDto = RestTemplateHepler.parse(responseResult, BoundPhoneDto.class);
                    order.setMappingId(boundPhoneDto.getAxbSubsId());
                    order.setMappingNumber(boundPhoneDto.getAxbSecretNo());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            order.setDriverGrabTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
            responseResult = otherInterfaceTask.updateMap(order, cityCode, carType, orderDtoRequest.getDriverLongitude(), orderDtoRequest.getDriverLatitude());
            if (responseResult.getCode() != BusinessInterfaceStatus.SUCCESS.getCode()) {
                return responseResult;
            }
            redisTemplate.delete(lock);
        } else {
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), GRAB_FAILURE);
        }
        return ResponseResult.success(json);
    }

    /**
     * 字符串转实体类
     *
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parse(String jsonStr, Class<T> clazz) {
        ObjectMapper om = new ObjectMapper();
        T readValue = null;
        try {
            readValue = om.readValue(jsonStr, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readValue;
    }
}
