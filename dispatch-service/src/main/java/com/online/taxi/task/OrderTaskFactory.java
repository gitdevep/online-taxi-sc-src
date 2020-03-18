package com.online.taxi.task;

import com.online.taxi.consts.OrderTypeEnum;
import com.online.taxi.task.impl.OrderAirportDropoffTask;
import com.online.taxi.task.impl.OrderAirportPickupTask;
import com.online.taxi.task.impl.OrderNormalTask;

/**
 * @date 2018/10/17
 */
public class OrderTaskFactory {

    public static ITask createTask(int orderId, int serviceTypeId, int type) {
        if (serviceTypeId == OrderTypeEnum.APPOINTMENT.getCode()) {
            return new OrderNormalTask(orderId, type);
        } else if (serviceTypeId == OrderTypeEnum.AIRPORT_PICKUP.getCode()) {
            return new OrderAirportPickupTask(orderId, type);
        } else if (serviceTypeId == OrderTypeEnum.AIRPORT_DROPOFF.getCode()) {
            return new OrderAirportDropoffTask(orderId, type);
        } else {
            return new OrderNormalTask(orderId, type);
        }
    }

}
