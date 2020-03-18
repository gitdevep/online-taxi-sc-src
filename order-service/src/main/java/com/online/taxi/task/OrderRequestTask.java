package com.online.taxi.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.taxi.constatnt.BusinessInterfaceStatus;
import com.online.taxi.constatnt.EnableDisableEnum;
import com.online.taxi.constatnt.OrderEnum;
import com.online.taxi.constatnt.OrderStatusEnum;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.map.Points;
import com.online.taxi.dto.map.request.RouteRequest;
import com.online.taxi.dto.valuation.charging.KeyRule;
import com.online.taxi.dto.valuation.charging.Rule;
import com.online.taxi.entity.*;
import com.online.taxi.util.RestTemplateHepler;
import com.online.taxi.dto.BaseInfoDto;
import com.online.taxi.mapper.*;
import com.online.taxi.request.OrderDtoRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单任务请求任务
 * @date 2018/9/1
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class OrderRequestTask {

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private ServiceTypeMapper serviceTypeMapper;

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    private CarLevelMapper carLevelMapper;

    @Autowired
    private OrderPointsMapper orderPointsMapper;

    @Autowired
    private OrderRuleMirrorMapper orderRuleMirrorMapper;

    @Autowired
    private OtherInterfaceTask otherInterfaceTask;


    /**
     * 校验基础信息
     * @param orderRequest
     * @return
     */
    public ResponseResult checkBaseInfo(OrderDtoRequest orderRequest){
        City city = cityMapper.selectByPrimaryKey(orderRequest.getCityCode());
        if(null == city){
            return ResponseResult.fail(OrderEnum.CITIES_DON_EXIST.getCode(),OrderEnum.CITIES_DON_EXIST.getValue());
        }else{
            if(StringUtils.isNotBlank(city.getCityStatus().toString()) && city.getCityStatus().equals(EnableDisableEnum.ENABLE.getCode())){
                return ResponseResult.fail(OrderEnum.CITIES_IS_NOT_ENABLED.getCode(),OrderEnum.CITIES_IS_NOT_ENABLED.getValue());
            }
        }
        ServiceType serviceType = serviceTypeMapper.selectByPrimaryKey(orderRequest.getServiceTypeId());
        if(null == serviceType){
            return ResponseResult.fail(OrderEnum.SERVICE_TYPE_IS_NO.getCode(),OrderEnum.SERVICE_TYPE_IS_NO.getValue());
        }else{
            if(StringUtils.isNotBlank(serviceType.getServiceTypeStatus().toString()) && serviceType.getServiceTypeStatus().equals(EnableDisableEnum.ENABLE.getCode())){
                return ResponseResult.fail(OrderEnum.SERVICE_TYPE_IS_NOT_ENABLED.getCode(),OrderEnum.SERVICE_TYPE_IS_NOT_ENABLED.getValue());
            }
        }
        Channel channel = channelMapper.selectByPrimaryKey(orderRequest.getChannelId());
        if(null == channel){
            return ResponseResult.fail(OrderEnum.CHANNEL_IS_NO.getCode(),OrderEnum.CHANNEL_IS_NO.getValue());
        }else{
            if(StringUtils.isNotBlank(channel.getChannelStatus().toString()) && channel.getChannelStatus().equals(EnableDisableEnum.ENABLE.getCode())){
                return ResponseResult.fail(OrderEnum.CHANNEL_IS_NOT_ENABLED.getCode(),OrderEnum.CHANNEL_IS_NOT_ENABLED.getValue());
            }
        }
        CarLevel carLevel = carLevelMapper.selectByPrimaryKey(orderRequest.getCarLevelId());
        if(null == carLevel){
            return ResponseResult.fail(OrderEnum.CAR_LEVEL_IS_NO.getCode(),OrderEnum.CAR_LEVEL_IS_NO.getValue());
        }else{
            if(StringUtils.isNotBlank(carLevel.getEnable().toString()) && carLevel.getEnable().equals(EnableDisableEnum.ENABLE.getCode())){
                return ResponseResult.fail(OrderEnum.CAR_LEVEL_IS_NOT_ENABLED.getCode(),OrderEnum.CAR_LEVEL_IS_NOT_ENABLED.getValue());
            }
        }
        BaseInfoDto baseInfoDto = new BaseInfoDto();
        baseInfoDto.setCityName(city.getCityName());
        baseInfoDto.setServiceTypeName(serviceType.getServiceTypeName());
        baseInfoDto.setChannelName(channel.getChannelName());
        baseInfoDto.setCarLevelName(carLevel.getLabel());
        return ResponseResult.success(baseInfoDto);
    }

    /**
     * 手机号解绑与更新orderPoint
     * @param status
     * @param newOrder
     * @return
     */
    public ResponseResult phoneUnbindAndInsertOrderPoint(Integer status,Order newOrder,Integer cancel){
        ResponseResult responseResult;
        if(null != status){
            if (OrderStatusEnum.STATUS_DRIVER_TRAVEL_END.getCode() == status.intValue()) {
                OrderRuleMirror orderRuleMirror = orderRuleMirrorMapper.selectByPrimaryKey(newOrder.getId());
                String originalRule = orderRuleMirror.getRule();
                Rule rule = parse(originalRule, Rule.class);
                KeyRule keyRule = rule.getKeyRule();
                //查询车辆轨迹点
                RouteRequest routeRequest = new RouteRequest();
                routeRequest.setVehicleId(newOrder.getCarId().toString());
                routeRequest.setCity(keyRule.getCityCode());
                routeRequest.setStartTime(newOrder.getReceivePassengerTime().getTime());
                routeRequest.setEndTime(newOrder.getPassengerGetoffTime().getTime());
                routeRequest.setCorrection("origin");
                responseResult = otherInterfaceTask.selectVehiclePoints(routeRequest);
                if (BusinessInterfaceStatus.SUCCESS.getCode() != responseResult.getCode()) {
                    return responseResult;
                }
                try{
                    Points points = RestTemplateHepler.parse(responseResult, Points.class);
                    OrderPoints orderPoints = new OrderPoints();
                    orderPoints.setDriverId(newOrder.getDriverId());
                    orderPoints.setCarId(newOrder.getCarId());
                    orderPoints.setOrderId(newOrder.getId());
                    orderPoints.setPoints(new ObjectMapper().writeValueAsString(points.getPoints()));
                    orderPointsMapper.insertSelective(orderPoints);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        if(null != status || null != cancel){
            if(null != status){
                if(OrderStatusEnum.STATUS_PAY_END.getCode() == status.intValue()){
                    if(null != newOrder.getMappingNumber()){
                        try {
                            otherInterfaceTask.phoneNumberUnbind(newOrder.getMappingId(), newOrder.getMappingNumber());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //它人手机号解绑
                    if (newOrder.getOrderType() == OrderEnum.ORDER_TYPE_OTHER.getCode()) {
                        if(null != newOrder.getOtherMappingId()){
                            otherInterfaceTask.phoneNumberUnbind(newOrder.getOtherMappingId(), newOrder.getOtherMappingNumber());
                        }
                    }
                }
            }
            if(null != cancel){
                if (OrderEnum.IS_CANCEL.getCode() == cancel.intValue() ) {
                    if(null != newOrder.getMappingNumber()){
                        try {
                            otherInterfaceTask.phoneNumberUnbind(newOrder.getMappingId(), newOrder.getMappingNumber());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //它人手机号解绑
                    if (newOrder.getOrderType() == OrderEnum.ORDER_TYPE_OTHER.getCode()) {
                        if(null != newOrder.getOtherMappingId()){
                            otherInterfaceTask.phoneNumberUnbind(newOrder.getOtherMappingId(), newOrder.getOtherMappingNumber());
                        }
                    }
                }
            }
        }
        return ResponseResult.success(null);
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
