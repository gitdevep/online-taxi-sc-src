package com.online.taxi.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 司机统计信息
 * 
 * @description
 * @author jxl
 * @version
 * @date 2018年1月30日
 */
public class DriverStatRequest extends BaseMPRequest {

    @JSONField(name = "Address")
    private Integer address;

    @JSONField(name = "LicenseId")
    private String licenseId;

    @JSONField(name = "Cycle")
    private Integer cycle;

    @JSONField(name = "OrderCount")
    private Integer orderCount;

    @JSONField(name = "TrafficViolationCount")
    private Integer trafficViolationCount;

    @JSONField(name = "ComplainedCount")
    private Integer complainedCount;

    @JSONField(name = "Flag")
    private Integer flag;

    @JSONField(name = "UpdateTime")
    private Long updateTime;

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getTrafficViolationCount() {
        return trafficViolationCount;
    }

    public void setTrafficViolationCount(Integer trafficViolationCount) {
        this.trafficViolationCount = trafficViolationCount;
    }

    public Integer getComplainedCount() {
        return complainedCount;
    }

    public void setComplainedCount(Integer complainedCount) {
        this.complainedCount = complainedCount;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public DriverStatRequest(String ipcType, String requestUrl) {
        this.ipcType = ipcType;
        this.requestUrl = requestUrl;
    }

    @Override
    public void setRequestUrl(String requestUrl) {
        super.requestUrl = requestUrl;
    }

}
