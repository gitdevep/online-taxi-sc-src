package com.online.taxi.constant;

import com.online.taxi.constatnt.CodeEnum;
import lombok.Getter;

/**
 * 
 */
@Getter
public enum RechargeTypeEnum implements CodeEnum {
    CHARGE(1, "仅充值"),
    CONSUME(2, "充值消费"),

	EX(999,"none");
    private final int code;
    private final String value;

    RechargeTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

}