package com.online.taxi.task.order;

import com.online.taxi.constatnt.OrderStatusEnum;
import com.online.taxi.data.upload.proto.OTIpcDef;
import com.online.taxi.dto.order.OrderCancelDto;
import com.online.taxi.dto.order.OrderDto;
import com.online.taxi.mapper.OrderMapper;
import com.online.taxi.task.AbstractSupervisionTask;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * 订单数据上报
 *
 * @date 2018/8/24
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OrderTask extends AbstractSupervisionTask {

    @NonNull
    private OrderMapper orderMapper;

    /**
     * 监听到插入操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean insert(Integer id) {
        return false;
    }

    /**
     * 监听到更新操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean update(Integer id) {
        return execute(id);
    }

    /**
     * 监听到删除操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean delete(Integer id) {
        return false;
    }

    private boolean execute(Integer id) {
        messageMap.clear();

        return tryComposeData(maxTimes, p -> {
            OrderDto order = null;
            OrderCancelDto cancelDto = null;
            try {
                order = orderMapper.selectByOrderId(id);
                if (OrderStatusEnum.STATUS_ORDER_START.getCode() == order.getStatus()) {
                    ipcType = OTIpcDef.IpcType.orderCreate;
                    messageMap.put("OrderId", order.getOrderNumber());
                    messageMap.put("DepartTime", formatDateTime(order.getOrderStartTime(), DateTimePatternEnum.DateTime));
                    messageMap.put("OrderTime", formatDateTime(order.getStartTime(), DateTimePatternEnum.DateTime));
                    messageMap.put("Departure", order.getStartAddress());
                    messageMap.put("DepLongitude", toCoordinates(order.getStartLongitude()));
                    messageMap.put("DepLatitude", toCoordinates(order.getStartLatitude()));
                    messageMap.put("Destination", order.getStartAddress());
                    messageMap.put("DestLongitude", toCoordinates(order.getEndLongitude()));
                    messageMap.put("DestLatitude", toCoordinates(order.getEndLatitude()));
                    messageMap.put("Encrypt", 1);
                    messageMap.put("FareType", order.getRuleId());
                    return true;
                } else if (OrderStatusEnum.STATUS_DRIVER_ACCEPT.getCode() == order.getStatus()) {
                    //订单请求成功，匹配到司机
                    ipcType = OTIpcDef.IpcType.orderMatch;
                    order = orderMapper.selectBeginningOrder(id);
                    messageMap.put("OrderId", order.getOrderNumber());
                    messageMap.put("Encrypt", 1);
                    messageMap.put("LicenseId", order.getDrivingLicenceNumber());
                    messageMap.put("DriverPhone", getPhoneNumber(order.getDriverPhone()));
                    messageMap.put("VehicleNo", order.getPlateNumber());
                    messageMap.put("DistributeTime", formatDateTime(order.getDriverGrabTime(), DateTimePatternEnum.DateTime));
                    return true;
                } else if (1 == order.getIsCancel()) {
                    //订单取消
                    ipcType = OTIpcDef.IpcType.orderCancel;
                    cancelDto = orderMapper.selectCancelDetail(id);
                    messageMap.put("OrderId", cancelDto.getOrderNumber());
                    messageMap.put("CancelTime", formatDateTime(cancelDto.getCreateTime(), DateTimePatternEnum.DateTime));
                    messageMap.put("Operator", 1 == cancelDto.getOperatorType() ? "1" : "3");
                    messageMap.put("CancelTypeCode", ObjectUtils.nullSafeEquals(cancelDto.getOperatorType(), 1) ? (ObjectUtils.nullSafeEquals(cancelDto.getIsCharge(), 1) ? "4" : "1") : "3");
                    return true;
                }
                return false;
            } catch (Exception e) {
                if (p == maxTimes) {
                    if (order != null && (OrderStatusEnum.STATUS_ORDER_START.getCode() == order.getStatus() || OrderStatusEnum.STATUS_DRIVER_ACCEPT.getCode() == order.getStatus())) {
                        log.error("数据上报异常：ipcType={}, id={}", ipcType.name(), id, e);
                    } else if (cancelDto != null && 1 == order.getIsCancel()) {
                        log.error("数据上报异常：ipcType={}, id={}", ipcType.name(), id, e);
                    }
                }
                return false;
            }
        });
    }
}
