package com.online.taxi.constant;

import com.online.taxi.constatnt.CodeEnum;
import lombok.Getter;

/**
 * 
 */
@Getter
public enum ResponseStatusEnum implements CodeEnum {
    /**冻结100-199*/
    NOT_ALLOW_RE_FREEZE(100, "重复冻结"),
    FREEZE_NOT_ENOUGH(101, "冻结余额不足"),
    FREEZE_RECORD_EMPTY(102,"冻结记录为空"),
    REFUND_MONEY_ERROR(103,"退款金额不正确"),
    REFUND_PAID_RECORD_EMPTY(104,"没有可退款的支付记录"),
    REFUND_PAID_RECORD_MONEY_ZERO(105,"可退款的金额为0"),
    PRICE_EMPTY(106,"可冻结金额为0"),

    /**钱包200-299*/
    PASSENGER_WALLET_EMPTY(200,"钱包为空"),


	EX(999,"none");
    private final int code;
    private final String value;

    ResponseStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

}