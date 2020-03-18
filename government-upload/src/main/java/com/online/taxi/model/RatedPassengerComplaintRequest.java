package com.online.taxi.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 乘客投诉信息请求报文
 * Created by wn on 2018/1/27.
 */
public class RatedPassengerComplaintRequest extends BaseMPRequest {

    @JSONField(name = "OrderId")
    private String orderId;

    @JSONField(name = "ComplaintTime")
    private Long complaintTime;

    @JSONField(name = "Detail")
    private String detail;

    public RatedPassengerComplaintRequest() {}

    public RatedPassengerComplaintRequest(String ipcType, String requestUrl) {
        this.ipcType = ipcType;
        this.requestUrl = requestUrl;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getComplaintTime() {
        return complaintTime;
    }

    public void setComplaintTime(Long complaintTime) {
        this.complaintTime = complaintTime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setRequestUrl(String requestUrl) {
        super.requestUrl = requestUrl;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
