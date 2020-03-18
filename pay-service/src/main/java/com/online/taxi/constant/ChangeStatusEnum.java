package com.online.taxi.constant;

import com.online.taxi.constatnt.CodeEnum;
import lombok.Getter;

/**
 * 
 */
@Getter
public enum ChangeStatusEnum implements CodeEnum {
    ADD(1, "加"),
    SUB(-1, "减"),

	EX(999,"none");
    private final int code;
    private final String value;

    ChangeStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

}