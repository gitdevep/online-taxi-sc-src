package com.online.taxi.dto;

import lombok.Data;

/**
 * @date 2018/8/21
 */
@Data
public class RefundPrice {
    Double refundCapital;
    Double refundGiveFee;

    public RefundPrice(Double refundCapital, Double refundGiveFee) {
        this.refundCapital = refundCapital;
        this.refundGiveFee = refundGiveFee;
    }
}
