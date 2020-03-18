package com.online.taxi.response;

import lombok.Data;

/**
 * @date 2018/8/17
 */
@Data
public class WeixinPayResponse {
    private String appId;
    private long timeStamp;
    private String nonceStr;
    private String packageData;
    private String prepayId;
    private String partnerId;
    private String signType;
    private String paySign;
    private String outTradeNo;
}
