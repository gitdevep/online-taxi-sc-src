package com.online.taxi.task;

import com.online.taxi.constatnt.AmapOrderEnum;
import com.online.taxi.constatnt.BusinessInterfaceStatus;
import com.online.taxi.constatnt.OrderEnum;
import com.online.taxi.constatnt.OrderStatusEnum;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.map.Route;
import com.online.taxi.dto.map.request.DistanceRequest;
import com.online.taxi.dto.map.request.OrderRequest;
import com.online.taxi.dto.map.request.RouteRequest;
import com.online.taxi.dto.phone.request.PhoneNumberRequest;
import com.online.taxi.dto.valuation.PriceResult;
import com.online.taxi.dto.valuation.charging.*;
import com.online.taxi.entity.*;
import com.online.taxi.util.RestTemplateHepler;
import com.online.taxi.dao.ChargeRuleDao;
import com.online.taxi.mapper.CarLevelMapper;
import com.online.taxi.request.OrderDtoRequest;
import com.online.taxi.utils.Distance;
import com.online.taxi.utils.ServicesConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 功能描述
 *
 * @date 2018/9/1
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class OtherInterfaceTask {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ServicesConfig servicesConfig;

    @Autowired
    private ChargeRuleDao chargeRuleDao;

    @Autowired
    private CarLevelMapper carLevelMapper;

    /**
     * 获取路途长度和行驶时间
     *
     * @param orderRequest
     * @return
     * @throws Exception
     */
    public ResponseResult requestRoute(OrderDtoRequest orderRequest) throws Exception {
        Route route;
        DistanceRequest distanceRequest = new DistanceRequest();
        distanceRequest.setOriginLongitude(orderRequest.getStartLongitude());
        distanceRequest.setOriginLatitude(orderRequest.getStartLatitude());
        distanceRequest.setDestinationLongitude(orderRequest.getEndLongitude());
        distanceRequest.setDestinationLatitude(orderRequest.getEndLatitude());
        Map<String, Object> map = object2Map(distanceRequest);
        String param = String.join("&", map.keySet().stream().map(k -> k + "={" + k + "}").collect(Collectors.toList()));
        try {
            ResponseResult responseResult = restTemplate.getForObject(servicesConfig.getMapAddress() + "/distance?" + param, ResponseResult.class, map);
            route = RestTemplateHepler.parse(responseResult, Route.class);
            log.info("测量距离返回={}", route);

            if (null == route.getDuration() || null == route.getDistance()) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "测量距离失败");
            }
            if (route.getDistance() <= 0) {
                return ResponseResult.fail(OrderEnum.TOO_CLOSE.getCode(), OrderEnum.TOO_CLOSE.getValue());
            }
            if (route.getDistance() > Distance.DISTANCE.getCode()) {
                return ResponseResult.fail(OrderEnum.TOO_FAR_AWAY.getCode(), OrderEnum.TOO_FAR_AWAY.getValue());
            }
            if(route.getDuration() >= Distance.DURATION.getCode()){
                return ResponseResult.fail(OrderEnum.TOO_FAR_AWAY.getCode(), "时间不能超过24小时");
            }
        } catch (Exception e) {
            log.error("调用接口Distance错误:", e);
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "测量距离失败");
        }
        return ResponseResult.success(route);
    }

    /**
     * 计价规则
     *
     * @param orderDtoRequest
     * @return
     */
    public ResponseResult getOrderChargeRule(OrderDtoRequest orderDtoRequest) {
        Rule rule = new Rule();
        ChargeRule chargeRule = new ChargeRule();
        chargeRule.setCityCode(orderDtoRequest.getCityCode());
        chargeRule.setServiceTypeId(orderDtoRequest.getServiceTypeId());
        chargeRule.setChannelId(orderDtoRequest.getChannelId());
        chargeRule.setCarLevelId(orderDtoRequest.getCarLevelId());
        CarLevel carLevel = carLevelMapper.selectByPrimaryKey(orderDtoRequest.getCarLevelId());
        List<ChargeRule> chargeRuleList = chargeRuleDao.selectByPrimaryKey(chargeRule);
        if (chargeRuleList.size() == 0 || chargeRuleList.size() > 1) {
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "无计价规则");
        }
        chargeRule = chargeRuleList.get(0);
        rule.setId(chargeRule.getId());
        KeyRule keyRule = new KeyRule();
        keyRule.setCityCode(orderDtoRequest.getCityCode());
        keyRule.setCityName(orderDtoRequest.getCityName());
        keyRule.setServiceTypeId(orderDtoRequest.getServiceTypeId());
        keyRule.setServiceTypeName(orderDtoRequest.getServiceTypeName());
        keyRule.setChannelId(orderDtoRequest.getChannelId());
        keyRule.setChannelName(orderDtoRequest.getChannelName());
        keyRule.setCarLevelId(orderDtoRequest.getCarLevelId());
        keyRule.setCarLevelName(carLevel.getLabel());
        rule.setKeyRule(keyRule);
        BasicRule basicRule = new BasicRule();
        basicRule.setLowestPrice(chargeRule.getLowestPrice());
        basicRule.setBasePrice(chargeRule.getBasePrice());
        rule.setBasicRule(basicRule);
        PriceRule priceRule = new PriceRule();
        List<TimeRule> timeRules = new ArrayList<>();
        priceRule.setPerKiloPrice(chargeRule.getPerKiloPrice());
        priceRule.setPerMinutePrice(chargeRule.getPerMinutePrice());
        priceRule.setTimeRules(timeRules);
        rule.setPriceRule(priceRule);
        if (!ObjectUtils.nullSafeEquals(chargeRule.getBaseKilo(), BigDecimal.ZERO.doubleValue()) && !ObjectUtils.nullSafeEquals(chargeRule.getBaseMinutes(), BigDecimal.ZERO.doubleValue())) {
            basicRule.setKilos(chargeRule.getBaseKilo());
            basicRule.setMinutes(chargeRule.getBaseMinutes());
        } else {
            basicRule.setKilos(0D);
            basicRule.setMinutes(0D);
            List<ChargeRuleDetail> chargeRuleDetailList = chargeRuleDao.chargeRuleDetailList(chargeRule.getId());
            for (ChargeRuleDetail chargeRuleDetail : chargeRuleDetailList) {
                TimeRule timeRule = new TimeRule();
                if(chargeRuleDetail.getStart() >= 24){
                    return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "计价规则错误");
                }
                if(chargeRuleDetail.getEnd() >= 24){
                    return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "计价规则错误");
                }
                if(chargeRuleDetail.getStart() > chargeRuleDetail.getEnd()){
                    return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "计价规则错误");
                }
                timeRule.setStart(chargeRuleDetail.getStart());
                timeRule.setEnd(chargeRuleDetail.getEnd());
                timeRule.setPerKiloPrice(chargeRuleDetail.getPerKiloPrice());
                timeRule.setPerMinutePrice(chargeRuleDetail.getPerMinutePrice());
                timeRules.add(timeRule);
            }
        }
        BeyondRule beyondRule = new BeyondRule();
        beyondRule.setStartKilo(chargeRule.getBeyondStartKilo());
        beyondRule.setPerKiloPrice(chargeRule.getBeyondPerKiloPrice());
        rule.setBeyondRule(beyondRule);
        NightRule nightRule = new NightRule();
        nightRule.setStart(chargeRule.getNightStart());
        nightRule.setEnd(chargeRule.getNightEnd());
        nightRule.setPerKiloPrice(chargeRule.getNightPerKiloPrice());
        nightRule.setPerMinutePrice(chargeRule.getNightPerMinutePrice());
        rule.setNightRule(nightRule);
        List<TagPrice> tagPriceList = new ArrayList<>();
        if(StringUtils.isNotBlank(orderDtoRequest.getUserFeature())){
            Map<String, Object> param = new HashMap<>(3);
            param.put("serviceTypeId",orderDtoRequest.getServiceTypeId());
            param.put("cityCode",orderDtoRequest.getCityCode());
            param.put("tagId",orderDtoRequest.getUserFeature());
            tagPriceList =chargeRuleDao.selectTapInfo(param);
        }
        rule.setTagPrices(tagPriceList);
        return ResponseResult.success(rule);
    }

    /**
     * 获取预估价格
     *
     * @param orderId
     * @return
     */
    public PriceResult getOrderPrice(int orderId) throws Exception {
        PriceResult priceResult;
        log.info("orderId={}", orderId);
        try {
            ResponseResult responseResult = restTemplate.getForObject(servicesConfig.getValuation() + "/valuation/forecast/" + orderId, ResponseResult.class);
            priceResult = RestTemplateHepler.parse(responseResult, PriceResult.class);
            log.info("预估价格返回数据={}", priceResult);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("调用接口Distance错误:", e);
            throw e;
        }
        return priceResult;
    }

    /**
     *确认预估价格
     * @param orderId
     * @return
     * @throws Exception
     */
    public ResponseResult donePrice(int orderId) throws Exception {
        ResponseResult responseResult;
        try {
            responseResult = restTemplate.getForObject(servicesConfig.getValuation() + "/valuation/forecast/done/" + orderId, ResponseResult.class);
        }catch(Exception e){
            e.printStackTrace();
            log.error("调用接口Distance错误:", e);
            throw e;
        }
        return responseResult;
    }
    /**
     * @param order
     * @param cityCode
     * @param carType
     * @return
     */
    public ResponseResult updateMap(Order order, Integer cityCode, Integer carType, String driverLongitude, String driverLatitude) {
        ResponseResult responseResult = null;
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderId(order.getId().toString());
        orderRequest.setCustomerDeviceId(order.getDeviceCode());
        orderRequest.setType(order.getServiceType());
        orderRequest.setOrderCity(cityCode.toString());
        orderRequest.setVehicleType(carType);
        //司机接单
        if (OrderStatusEnum.STATUS_DRIVER_ACCEPT.getCode() == order.getStatus()) {
            orderRequest.setStatus(AmapOrderEnum.ASSIGN.getCode());
        }

        //司机去接乘客
        if (OrderStatusEnum.STATUS_RESERVED_ORDER_TO_PICK_UP.getCode() == order.getStatus()) {
            orderRequest.setStatus(AmapOrderEnum.START_SERVICE.getCode());
        }
        //乘客上车，司机开始行程
        if (OrderStatusEnum.STATUS_DRIVER_TRAVEL_START.getCode() == order.getStatus()) {
            orderRequest.setStatus(AmapOrderEnum.CHARGE_START.getCode());
        }
        //订单取消
        if (order.getIsCancel() != null && order.getIsCancel() == OrderEnum.IS_CANCEL.getCode() && OrderStatusEnum.STATUS_PAY_START.getCode() > order.getStatus()) {
            orderRequest.setStatus(AmapOrderEnum.CANCEL_ORDER.getCode());
        }
        //发起收款,未支付
        if (OrderStatusEnum.STATUS_PAY_START.getCode() == order.getStatus() && OrderEnum.NOT_PAY.getCode() == order.getIsPaid()) {
            orderRequest.setStatus(AmapOrderEnum.TO_PAY.getCode());
        }
        //订单已评价
        if (OrderEnum.IS_PAY.getCode() == order.getIsPaid() && OrderEnum.IS_EVALUATE.getCode() == order.getIsEvaluate() && OrderStatusEnum.STATUS_PAY_END.getCode() == order.getStatus()) {
            orderRequest.setStatus(AmapOrderEnum.EVALUATE_DO.getCode());
        }

        //到达目的地，行程结束，未支付
        if (OrderStatusEnum.STATUS_DRIVER_TRAVEL_END.getCode() == order.getStatus() && OrderEnum.NOT_PAY.getCode() == order.getIsPaid()) {
            orderRequest.setStatus(AmapOrderEnum.END_TRAVEL.getCode());
        }

        //支付完成,未评价
        if (OrderStatusEnum.STATUS_PAY_END.getCode() == order.getStatus() && OrderEnum.IS_PAY.getCode() == order.getIsPaid() && OrderEnum.NOT_EVALUATE.getCode() == order.getIsEvaluate()) {
            orderRequest.setStatus(AmapOrderEnum.TO_EVALUATE.getCode());
        }

        //取消订单未支付
        if (order.getIsCancel() != null && order.getIsCancel() == OrderEnum.IS_CANCEL.getCode() && OrderStatusEnum.STATUS_PAY_START.getCode() == order.getStatus()) {
            orderRequest.setStatus(AmapOrderEnum.CANCEL_ORDER_TO_PAY.getCode());
        }

        orderRequest.setStartLongitude(order.getStartLongitude());
        orderRequest.setStartLatitude(order.getStartLatitude());
        orderRequest.setStartName(order.getStartAddress());
        orderRequest.setEndLongitude(order.getEndLongitude());
        orderRequest.setEndLatitude(order.getEndLatitude());
        orderRequest.setEndName(order.getEndAddress());
        orderRequest.setUserLongitude(order.getUserLongitude());
        orderRequest.setUserLatitude(order.getUserLatitude());
        orderRequest.setVehicleLongitude(driverLongitude);
        orderRequest.setVehicleLatitude(driverLatitude);
        try {
            responseResult = restTemplate.postForObject(servicesConfig.getMapAddress() + "/order", orderRequest, ResponseResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseResult;
    }

    /**
     * 查询车辆轨迹点
     *
     * @param routeRequest
     * @return
     */
    public ResponseResult selectVehiclePoints(RouteRequest routeRequest) {
        ResponseResult responseResult = null;
        try {
            Map<String, Object> map = object2Map(routeRequest);
            log.info("map={}", map);
            String param = String.join("&", map.keySet().stream().map(k -> k + "={" + k + "}").collect(Collectors.toList()));
            responseResult = restTemplate.getForObject(servicesConfig.getMapAddress() + "/route/points?" + param, ResponseResult.class, map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取车辆轨迹错误");
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "获取车辆轨迹错误");
        }
        return responseResult;
    }

    /**
     * 手机号绑定
     * @param startTime
     * @param driverPhone
     * @param passengerPhone
     * @return
     */
    public ResponseResult phoneNumberBind(Date startTime, String driverPhone, String passengerPhone){
        ResponseResult responseResult = null;

        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        cal.add(Calendar.DATE,1);
        startTime =cal.getTime();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PhoneNumberRequest phoneNumberRequest = new PhoneNumberRequest();
        phoneNumberRequest.setDriverPhone(driverPhone);
        phoneNumberRequest.setPassengerPhone(passengerPhone);
        phoneNumberRequest.setExpiration(format.format(startTime));
        Map<String, Object> map = object2Map(phoneNumberRequest);
        String param = String.join("&", map.keySet().stream().map(k -> k + "={" + k + "}").collect(Collectors.toList()));
        try {
            responseResult = restTemplate.getForObject(servicesConfig.getFile() + "/phone/bind?"+param, ResponseResult.class,map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseResult;
    }

    /**
     * 手机号解绑
     * @param mappingId
     * @param mappingNumber
     * @return
     */
    public ResponseResult phoneNumberUnbind(String mappingId,String mappingNumber){
        ResponseResult responseResult = null;
        PhoneNumberRequest phoneNumberRequest = new PhoneNumberRequest();
        phoneNumberRequest.setSubsId(mappingId);
        phoneNumberRequest.setSecretNo(mappingNumber);
        Map<String, Object> map = object2Map(phoneNumberRequest);
        String param = String.join("&", map.keySet().stream().map(k -> k + "={" + k + "}").collect(Collectors.toList()));
        try {
            responseResult = restTemplate.getForObject(servicesConfig.getFile() + "/phone/unbind?"+param, ResponseResult.class,map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseResult;
    }


    /**
     * 实体对象转成Map
     *
     * @param obj 实体对象
     * @return
     */
    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map = new HashMap<>(281);
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
