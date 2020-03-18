package com.online.taxi.dto.rated;

import lombok.Data;

import java.util.Date;

/**
 * 乘客投诉信息DTO
 *
 * @date 2018/9/1
 */
@Data
public class RatedPassengerComplaintDto {

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 评价时间
     */
    private Date updateTime;

    /**
     * 投诉内容
     */
    private String content;
}
