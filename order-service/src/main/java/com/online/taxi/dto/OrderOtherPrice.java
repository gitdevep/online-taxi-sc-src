package com.online.taxi.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 功能描述
 *
 * @date 2018/8/25
 */
@Data
public class OrderOtherPrice {
    private Integer orderId;
    private Integer passengerId;
    private BigDecimal totalPrice;
}
