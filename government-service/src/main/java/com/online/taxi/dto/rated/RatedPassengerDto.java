package com.online.taxi.dto.rated;

import lombok.Data;

import java.util.Date;

/**
 * 乘客评价信息DTO
 *
 * @date 2018/9/1
 */
@Data
public class RatedPassengerDto {
    /**
     * PK
     */
    private Integer id;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 评价时间
     */
    private Date updateTime;

    /**
     * 服务满意度
     */
    private Long grade;
}
