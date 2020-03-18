package com.online.taxi.request;

import lombok.Data;

/**
 * @date 2018/8/21
 */
@Data
public class ConsumeRequest {

    /**
     * 正常扣款金额
     */
    private Double price;

    /**
     * 尾款金额
     */
    private Double tailPrice;

    /**
     * 补扣宽金额
     */
    private Double replenishPrice;

    private Integer orderId;

    private Integer yid;
}
