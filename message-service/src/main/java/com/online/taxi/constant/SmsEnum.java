package com.online.taxi.constant;

import com.online.taxi.constatnt.CodeEnum;
import lombok.Getter;

/**
 * 
 */
@Getter
public enum SmsEnum implements CodeEnum {

	/**
     * 短信发送成功
     */
    OK(0, "amap sccess"),
	/**
     * 短信请求失败
     */
    FAIL(1, "amap fail"),
	/**
     * 短信请求异常
     */
    EXCEPTION(2, "amap exception"),




	EX(999,"none");
    private final int code;
    private final String value;

    SmsEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

}