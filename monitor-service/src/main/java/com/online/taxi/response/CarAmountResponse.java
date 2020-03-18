package com.online.taxi.response;

import lombok.Data;

/**
 * 功能描述
 *
 * @date 2018/10/17
 */
@Data
public class CarAmountResponse {
    /**
     * 日期
     */
    private String day;

    /**
     * 类型
     */
    private String type;

    /**
     * 总数
     */
    private int count;
}
