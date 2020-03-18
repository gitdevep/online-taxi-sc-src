package com.online.taxi.response;

import lombok.Data;

/**
 * 功能描述
 *
 * @date 2018/10/15
 */
@Data
public class UserResponse {

    /**
     * 日期
     */
    private String day;

    /**
     * 数量
     */
    private int count;

    /**
     * 手机系统1：ios 2：Android
     */
    private String type;

    /**
     * 1:实时订单 2：预约订单
     */
    private String serviceType;
}
