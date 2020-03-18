package com.online.taxi.response;

import lombok.Data;

/**
 * 优惠券response
 * @date 2018/10/17
 */
@Data
public class DiscountCouponsResponse {

    /**
     * 日期
     */
    private String day;

    /**
     *类型
     */
    private String type;

    /**
     *结果
     */
    private int count;
}
