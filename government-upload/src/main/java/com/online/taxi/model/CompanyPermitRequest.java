package com.online.taxi.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @description 报部：公司经营许可
 * @author jxl
 * @version
 * @date 2018年1月29日
 */
public class CompanyPermitRequest extends BaseMPRequest {

    @JSONField(name = "Address")
    private Integer address;

    @JSONField(name = "Certificate")
    private String certificate;

    @JSONField(name = "OperationArea")
    private String operationArea;

    @JSONField(name = "OwnerName")
    private String ownerName;

    @JSONField(name = "Organization")
    private String organization;

    @JSONField(name = "StartDate")
    private Long startDate;

    @JSONField(name = "StopDate")
    private Long stopDate;

    @JSONField(name = "CertifyDate")
    private Long certifyDate;

    @JSONField(name = "State")
    private String state;

    @JSONField(name = "Flag")
    private Integer flag;

    @JSONField(name = "UpdateTime")
    private Long updateTime;

    public CompanyPermitRequest(String ipcType, String requestUrl) {
        this.ipcType = ipcType;
        this.requestUrl = requestUrl;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getOperationArea() {
        return operationArea;
    }

    public void setOperationArea(String operationArea) {
        this.operationArea = operationArea;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getStopDate() {
        return stopDate;
    }

    public void setStopDate(Long stopDate) {
        this.stopDate = stopDate;
    }

    public Long getCertifyDate() {
        return certifyDate;
    }

    public void setCertifyDate(Long certifyDate) {
        this.certifyDate = certifyDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    @Override
    public void setRequestUrl(String requestUrl) {
        super.requestUrl = requestUrl;
    }

}
