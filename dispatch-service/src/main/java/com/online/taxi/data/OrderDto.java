package com.online.taxi.data;

import com.online.taxi.entity.Order;

/**
 * @date 2018/8/30
 */
public class OrderDto extends Order {
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    private Integer orderId;

    private Integer updateType;

    public Integer getUpdateType() {
        return updateType;
    }

    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
    }
}
