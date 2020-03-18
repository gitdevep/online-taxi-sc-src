package com.online.taxi.dto;

import lombok.Data;

/**
 * 功能描述
 *
 * @date 2018/8/17
 */
@Data
public class OrderKeyRuleDto {
    private Integer orderId;
    private Integer cityCode;
    private Integer serviceTypeId;
    private Integer channelId;
    private Integer carTypeId;
}
