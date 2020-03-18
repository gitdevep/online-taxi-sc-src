package com.online.taxi.constant;

import com.online.taxi.constatnt.CodeEnum;
import lombok.Getter;

/**
 * 
 */
@Getter
public enum FreezeStatusEnum implements CodeEnum {
    FREEZE(1, "冻结"),
    UNFREEZE(0, "解冻"),

	EX(999,"none");
    private final int code;
    private final String value;

    FreezeStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

}