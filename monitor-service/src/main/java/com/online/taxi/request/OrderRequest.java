package com.online.taxi.request;

import lombok.Data;


/**
 * 订单request
 *
 * @date 2018/10/15
 */
@Data
public class OrderRequest {

    /**
     * 查看类目：1：订单统计、2：订单流水
     */
    private String check;

    /**
     * 查询周期
     * 1：天、2：月
     */
    private String period;

    /**
     * 开始时间
     */
    private String begin;

    /**
     * 结束时间
     */
    private String end;
}
