package com.online.taxi.dto;

import lombok.Data;

/**
 * 功能描述
 *
 * @date 2018/8/25
 */
@Data
public class OrderPrice {
    /**
     * 订单id
     */
    private Integer orderId;
    /**
     * 订单价格
     */
    private Double price;
}
