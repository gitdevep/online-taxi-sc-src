package com.online.taxi.constant;

/**
 * @date 2018/8/17
 */
public class PayConst {

    public static final String UNDER_LINE = "_";

    public static final String CHARSET = "UTF-8";

    public static final String RETURN_CODE_SUCCESS = "SUCCESS";

    /**
     * 已支付
     */
    public static final String ERROR_ORDER_PAID = "ORDERPAID";

    /**
     * 商户订单号重复
     */
    public static final String ERROR_OUT_TRADE_NO_USED = "OUT_TRADE_NO_USED";

    /**
     * 订单关闭
     */
    public static final String ERROR_ORDER_CLOSED = "ORDERCLOSED";

    /**
     *  支付宝支付成功
     */
    public static final String TRADE_SUCCESS = "TRADE_SUCCESS";

    /**
     * 微信未支付
     */
    public static final String NOTPAY = "NOTPAY";

    /**
     * 支付结果 0：成功，1：重试，2：无订单
     */
    public static final Integer PAY_SUCCESS_STATUS = 0;
    public static final Integer RE_TRY_STATUS = 1;
    public static final Integer NO_ORDER_STATUS = 2;

    public static final Double ZERO = 0d;

}
