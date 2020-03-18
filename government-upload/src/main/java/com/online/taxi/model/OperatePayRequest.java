package com.online.taxi.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 经营支付信息请求报文
 * Created by wn on 2018/1/29.
 */
public class OperatePayRequest extends BaseMPRequest {

    @JSONField(name = "OrderId")
    private String orderId;

    @JSONField(name = "OnArea")
    private Integer onArea;

    @JSONField(name = "LicenseId")
    private String licenseId;

    @JSONField(name = "FareType")
    private String fareType;

    @JSONField(name = "VehicleNo")
    private String vehicleNo;

    @JSONField(name = "BookDepTime")
    private Long bookDepTime;

    @JSONField(name = "DepLongitude")
    private Long depLongitude;

    @JSONField(name = "DepLatitude")
    private Long depLatitude;

    @JSONField(name = "DepTime")
    private Long depTime;

    @JSONField(name = "DestLongitude")
    private Long destLongitude;

    @JSONField(name = "DestLatitude")
    private Long destLatitude;

    @JSONField(name = "DestTime")
    private Long destTime;

    @JSONField(name = "DriveMile")
    private Long driveMile;

    @JSONField(name = "DriveTime")
    private Long driveTime;

    @JSONField(name = "FactPrice")
    private Double factPrice;

    @JSONField(name = "FarUpPrice")
    private Double farUpPrice;

    @JSONField(name = "OtherUpPrice")
    private Double otherUpPrice;

    @JSONField(name = "PayState")
    private String payState;

    @JSONField(name = "InvoiceStatus")
    private String invoiceStatus;

    public OperatePayRequest() {}

    public OperatePayRequest(String ipcType, String requestUrl) {
        this.ipcType = ipcType;
        this.requestUrl = requestUrl;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getOnArea() {
        return onArea;
    }

    public void setOnArea(Integer onArea) {
        this.onArea = onArea;
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

    public Long getBookDepTime() {
        return bookDepTime;
    }

    public void setBookDepTime(Long bookDepTime) {
        this.bookDepTime = bookDepTime;
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

    public Long getDepTime() {
        return depTime;
    }

    public void setDepTime(Long depTime) {
        this.depTime = depTime;
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

    public Long getDestTime() {
        return destTime;
    }

    public void setDestTime(Long destTime) {
        this.destTime = destTime;
    }

    public Long getDriveMile() {
        return driveMile;
    }

    public void setDriveMile(Long driveMile) {
        this.driveMile = driveMile;
    }

    public Long getDriveTime() {
        return driveTime;
    }

    public void setDriveTime(Long driveTime) {
        this.driveTime = driveTime;
    }

    public Double getFactPrice() {
        return factPrice;
    }

    public void setFactPrice(Double factPrice) {
        this.factPrice = factPrice;
    }

    public Double getFarUpPrice() {
        return farUpPrice;
    }

    public void setFarUpPrice(Double farUpPrice) {
        this.farUpPrice = farUpPrice;
    }

    public Double getOtherUpPrice() {
        return otherUpPrice;
    }

    public void setOtherUpPrice(Double otherUpPrice) {
        this.otherUpPrice = otherUpPrice;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public void setRequestUrl(String requestUrl) {
        super.requestUrl = requestUrl;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
