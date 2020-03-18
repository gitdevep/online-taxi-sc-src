package com.online.taxi.constant;

import com.online.taxi.constatnt.CodeEnum;
import lombok.Getter;

/**
 * 
 */
@Getter
public enum PayEnum implements CodeEnum {

	/**
     * 已支付
     */
    PAID(1, "已支付"),

	/**
     * 未支付
     */
    UN_PAID(0, "未支付"),

    /**
     * 扣款价格不正确
     */
    PRICE_ERROR(2,"扣款价格不正确"),

    /**
     * 余额不足，请充值
     */
    BALANCE_NOT_ENOUGH(3,"余额不足，请充值"),


	EX(999,"none");
    private final int code;
    private final String value;

    PayEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

}