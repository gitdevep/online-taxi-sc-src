package com.online.taxi.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 乘客评价信息请求报文
 * Created by wn on 2018/1/27.
 */
public class RatedPassengerRequest extends BaseMPRequest {

    @JSONField(name = "OrderId")
    private String orderId;

    @JSONField(name = "EvaluateTime")
    private Long evaluateTime;

    @JSONField(name = "ServiceScore")
    private Long serviceScore;

    public RatedPassengerRequest() {}

    public RatedPassengerRequest(String ipcType, String requestUrl) {
        this.ipcType = ipcType;
        this.requestUrl = requestUrl;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(Long evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    public Long getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(Long serviceScore) {
        this.serviceScore = serviceScore;
    }

    public void setRequestUrl(String requestUrl) {
        super.requestUrl = requestUrl;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
