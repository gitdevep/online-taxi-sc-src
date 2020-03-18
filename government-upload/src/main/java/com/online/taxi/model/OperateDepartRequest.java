package com.online.taxi.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 经营出发信息请求报文
 * Created by wn on 2018/1/29.
 */
public class OperateDepartRequest extends BaseMPRequest {

    @JSONField(name = "OrderId")
    private String orderId;

    @JSONField(name = "LicenseId")
    private String licenseId;

    @JSONField(name = "FareType")
    private String fareType;

    @JSONField(name = "VehicleNo")
    private String vehicleNo;

    @JSONField(name = "DepLongitude")
    private Long depLongitude;

    @JSONField(name = "DepLatitude")
    private Long depLatitude;

    @JSONField(name = "Encrypt")
    private Integer encrypt;

    @JSONField(name = "DepTime")
    private Long depTime;

    public OperateDepartRequest() {}

    public OperateDepartRequest(String ipcType, String requestUrl) {
        this.ipcType = ipcType;
        this.requestUrl = requestUrl;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getFareType() {
        return fareType;
    }

    public void setFareType(String fareType) {
        this.fareType = fareType;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
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

    public Integer getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(Integer encrypt) {
        this.encrypt = encrypt;
    }

    public Long getDepTime() {
        return depTime;
    }

    public void setDepTime(Long depTime) {
        this.depTime = depTime;
    }

    public void setRequestUrl(String requestUrl) {
        super.requestUrl = requestUrl;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
