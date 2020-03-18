package com.online.taxi.constant;

import com.online.taxi.constatnt.CodeEnum;
import lombok.Getter;

/**
 * 
 */
@Getter
public enum JpushEnum implements CodeEnum {
	
	/**
     * 极光请求成功
     */
    OK(0, "jpush sccess"),
	/**
     * 极光接口请求失败
     */
    FAIL(1, "amap fail"),
	/**
     * 极光请求异常
     */
    EXCEPTION(2, "amap exception"),

    IDENTITY_EMPTY(100,"身份为空"),
    AUDIENCE_EMPTY(101,"听众标识为空"),
    AUDIENCE_ERROR(102,"听众标识错误"),
    PLATFORM_EMPTY(103,"目标平台为空"),
    PLATFORM_ERROR(104,"目标平台错误"),
    PUSH_ACCOUNT_EMPTY(105,"极光信息为空"),
    PUSH_CHANNEL_EMPTY(106,"极光渠道为空"),


	EX(999,"none");
    private final int code;
    private final String value;

    JpushEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

}