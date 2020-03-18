package com.online.taxi.request;

import lombok.Data;

/**
 * @date 2018/8/21
 */
@Data
public class FreezeRequest {

    /**
     * 冻结解冻金额
     */
    private Double price;

    private Integer orderId;

    private Integer yid;

    private Double limitPrice;
}
