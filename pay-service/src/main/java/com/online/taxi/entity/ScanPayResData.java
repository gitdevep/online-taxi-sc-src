package com.online.taxi.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * 被扫支付提交Post数据给到API之后，API会返回XML格式的数据，这个类用来装这些数据
 */
@Data
@XStreamAlias("xml")
public class ScanPayResData {
    /**协议层*/
    @XStreamAlias("return_code")
    private String returnCode;

    @XStreamAlias("return_msg")
    private String returnMsg ;

    /**协议返回的具体数据（以下字段在return_code 为SUCCESS 的时候有返回）*/
    @XStreamAlias("appid")
    private String appid ;

    @XStreamAlias("mch_id")
    private String mchId ;

    @XStreamAlias("sub_mch_id")
    private String subMchId ;

    @XStreamAlias("nonce_str")
    private String nonceStr ;

    @XStreamAlias("sign")
    private String sign ;

    @XStreamAlias("result_code")
    private String resultCode;

    @XStreamAlias("err_code")
    private String errCode ;

    @XStreamAlias("err_code_des")
    private String errCodeDes ;

    @XStreamAlias("tradeState")
    private String trade_state;

    @XStreamAlias("device_info")
    private String deviceInfo ;

    /**业务返回的具体数据（以下字段在return_code 和result_code 都为SUCCESS 的时候有返回）*/
    @XStreamAlias("openid")
    private String openid ;

    @XStreamAlias("is_subscribe")
    private String isSubscribe ;

    @XStreamAlias("trade_type")
    private String tradeType ;

    @XStreamAlias("bank_type")
    private String bankType;

    @XStreamAlias("total_fee")
    private String totalFee;

    @XStreamAlias("coupon_fee")
    private String couponFee ;

    @XStreamAlias("fee_type")
    private String feeType ;

    @XStreamAlias("transaction_id")
    private String transactionId ;

    @XStreamAlias("out_trade_no")
    private String outTradeNo ;

    @XStreamAlias("attach")
    private String attach;

    @XStreamAlias("time_end")
    private String timeEnd ;

    @XStreamAlias("prepay_id")
    private String prepayId;

}