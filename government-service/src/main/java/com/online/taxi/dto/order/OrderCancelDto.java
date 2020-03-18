package com.online.taxi.dto.order;

import lombok.Data;

import java.util.Date;

/**
 * 订单取消DTO
 *
 * @date 2018/8/29
 */
@Data
public class OrderCancelDto {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 乘客是否有责
     * 0：无责
     * 1：有责
     */
    private Integer isCharge;

    /**
     * 撤销发起方
     */
    private Integer operatorType;

    /**
     * 取消时间
     */
    private Date createTime;
}
