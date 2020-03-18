package com.online.taxi.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单统计response
 *
 * @date 2018/10/16
 */
@Data
public class OrderStatisticsResponse {
    /**
     * 日期
     */
    private String day;

    /**
     * 其它类
     */
    private String serviceType;

    /**
     * 订单总数
     */
    private int orderCount;

    /**
     * 订单总金额
     */
    private BigDecimal orderAmount;
}
