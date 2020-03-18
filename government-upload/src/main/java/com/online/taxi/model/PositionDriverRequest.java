package com.online.taxi.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 驾驶员定位信息请求报文
 * Created by wn on 2018/1/29.
 */
public class PositionDriverRequest extends BaseMPRequest {

    @JSONField(name = "LicenseId")
    private String licenseId;

    @JSONField(name = "DriverRegionCode")
    private Integer driverRegionCode;

    @JSONField(name = "VehicleNo")
    private String vehicleNo;

    @JSONField(name = "PositionTime")
    private Long positionTime;

    @JSONField(name = "Longitude")
    private Long longitude;

    @JSONField(name = "Latitude")
    private Long latitude;

    @JSONField(name = "Encrypt")
    private Integer encrypt;

    @JSONField(name = "OrderId")
    private String orderId;

    public PositionDriverRequest() {
    }

    public PositionDriverRequest(String ipcType, String requestUrl) {
        this.ipcType = ipcType;
        this.requestUrl = requestUrl;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public Integer getDriverRegionCode() {
        return driverRegionCode;
    }

    public void setDriverRegionCode(Integer driverRegionCode) {
        this.driverRegionCode = driverRegionCode;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public Long getPositionTime() {
        return positionTime;
    }

    public void setPositionTime(Long positionTime) {
        this.positionTime = positionTime;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Integer getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(Integer encrypt) {
        this.encrypt = encrypt;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public void setRequestUrl(String requestUrl) {
        super.requestUrl = requestUrl;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
