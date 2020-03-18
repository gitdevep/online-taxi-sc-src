package com.online.taxi.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 驾驶员处罚信息请求报文
 * Created by wn on 2018/1/29.
 */
public class RatedDriverPunishRequest extends BaseMPRequest {

    @JSONField(name = "LicenseId")
    private String licenseId;

    @JSONField(name = "PunishTime")
    private Long punishTime;

    @JSONField(name = "PunishResult")
    private String punishResult;

    public RatedDriverPunishRequest() {
    }

    public RatedDriverPunishRequest(String ipcType, String requestUrl) {
        this.ipcType = ipcType;
        this.requestUrl = requestUrl;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public Long getPunishTime() {
        return punishTime;
    }

    public void setPunishTime(Long punishTime) {
        this.punishTime = punishTime;
    }

    public String getPunishResult() {
        return punishResult;
    }

    public void setPunishResult(String punishResult) {
        this.punishResult = punishResult;
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
