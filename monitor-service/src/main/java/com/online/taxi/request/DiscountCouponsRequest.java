package com.online.taxi.request;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 优惠券request
 *
 * @date 2018/10/15
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class DiscountCouponsRequest {


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
