package com.online.taxi.constant;

import com.online.taxi.constatnt.CodeEnum;
import lombok.Getter;

/**
 * 
 */
@Getter
public enum PayTypeEnum implements CodeEnum {
    WXPAY(1, "微信支付"),
    BALANCE(2, "余额支付"),
    SYSTEM(3, "平台账户"),
    ALIPAY(4, "支付宝支付"),

	EX(999,"none");
    private final int code;
    private final String value;

    PayTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

}