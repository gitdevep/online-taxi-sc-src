package com.online.taxi.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 订单发起信息请求报文
 * Created by wn on 2018/1/29.
 */
public class OrderCreateRequest extends BaseMPRequest {

    @JSONField(name = "Address")
    private Integer address;

    @JSONField(name = "OrderId")
    private String orderId;

    @JSONField(name = "DepartTime")
    private Long departTime;

    @JSONField(name = "OrderTime")
    private Long orderTime;

    @JSONField(name = "Departure")
    private String departure;

    @JSONField(name = "DepLongitude")
    private Long depLongitude;

    @JSONField(name = "DepLatitude")
    private Long depLatitude;

    @JSONField(name = "Destination")
    private String destination;

    @JSONField(name = "DestLongitude")
    private Long destLongitude;

    @JSONField(name = "DestLatitude")
    private Long destLatitude;

    @JSONField(name = "Encrypt")
    private Integer encrypt;

    @JSONField(name = "FareType")
    private String fareType;

    public OrderCreateRequest() {}

    public OrderCreateRequest(String ipcType, String requestUrl) {
        this.ipcType = ipcType;
        this.requestUrl = requestUrl;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getDepartTime() {
        return departTime;
    }

    public void setDepartTime(Long departTime) {
        this.departTime = departTime;
    }

    public Long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Long orderTime) {
        this.orderTime = orderTime;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public Long getDepLongitude() {
        return depLongitude;
    }

    public void setDepLongitude(Long depLongitude) {
        this.depLongitude = depLongitude;
    }

    public Long getDepLatitude() {
        return depLatitude;
    }

    public void setDepLatitude(Long depLatitude) {
        this.depLatitude = depLatitude;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Long getDestLongitude() {
        return destLongitude;
    }

    public void setDestLongitude(Long destLongitude) {
        this.destLongitude = destLongitude;
    }

    public Long getDestLatitude() {
        return destLatitude;
    }

    public void setDestLatitude(Long destLatitude) {
        this.destLatitude = destLatitude;
    }

    public Integer getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(Integer encrypt) {
        this.encrypt = encrypt;
    }

    public String getFareType() {
        return fareType;
    }

    public void setFareType(String fareType) {
        this.fareType = fareType;
    }

    public void setRequestUrl(String requestUrl) {
        super.requestUrl = requestUrl;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
