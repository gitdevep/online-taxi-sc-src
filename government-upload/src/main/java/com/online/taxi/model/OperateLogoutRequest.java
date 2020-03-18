package com.online.taxi.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 车辆经营下线信息请求报文
 * Created by wn on 2018/1/29.
 */
public class OperateLogoutRequest extends BaseMPRequest {

    @JSONField(name = "LicenseId")
    private String licenseId;

    @JSONField(name = "VehicleNo")
    private String vehicleNo;

    @JSONField(name = "LogoutTime")
    private Long logoutTime;

    @JSONField(name = "Encrypt")
    private Integer encrypt;

    public OperateLogoutRequest() {}

    public OperateLogoutRequest(String ipcType, String requestUrl) {
        this.ipcType = ipcType;
        this.requestUrl = requestUrl;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public Long getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Long logoutTime) {
        this.logoutTime = logoutTime;
    }

    public Integer getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(Integer encrypt) {
        this.encrypt = encrypt;
    }

    public void setRequestUrl(String requestUrl) {
        super.requestUrl = requestUrl;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
