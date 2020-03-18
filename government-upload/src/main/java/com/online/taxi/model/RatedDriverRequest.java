package com.online.taxi.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 驾驶员信誉信息请求报文
 * Created by wn on 2018/1/29.
 */
public class RatedDriverRequest extends BaseMPRequest {

    @JSONField(name = "LicenseId")
    private String licenseId;

    @JSONField(name = "Level")
    private Long level;

    @JSONField(name = "TestDate")
    private Long testDate;

    @JSONField(name = "TestDepartment")
    private String testDepartment;

    public RatedDriverRequest() {
    }

    public RatedDriverRequest(String ipcType, String requestUrl) {
        this.ipcType = ipcType;
        this.requestUrl = requestUrl;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getTestDate() {
        return testDate;
    }

    public void setTestDate(Long testDate) {
        this.testDate = testDate;
    }

    public String getTestDepartment() {
        return testDepartment;
    }

    public void setTestDepartment(String testDepartment) {
        this.testDepartment = testDepartment;
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
