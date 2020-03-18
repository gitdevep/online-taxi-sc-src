package com.online.taxi.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * 
 * @description 报部：公司基本信息请求报文
 * @author jxl
 * @version
 * @date 2018年1月29日
 */
public class CompanyRequest extends BaseMPRequest {

    @JSONField(name = "CompanyName")
    private String companyName;

    @JSONField(name = "Identifier")
    private String identifier;

    @JSONField(name = "Address")
    private Integer address;

    @JSONField(name = "BusinessScope")
    private String businessScope;

    @JSONField(name = "ContactAddress")
    private String contactAddress;

    @JSONField(name = "EconomicType")
    private String economicType;

    @JSONField(name = "RegCapital")
    private String regCapital;

    @JSONField(name = "LegalName")
    private String legalName;

    @JSONField(name = "LegalID")
    private String legalId;

    @JSONField(name = "LegalPhone")
    private String legalPhone;

    @JSONField(name = "LegalPhoto")
    private String legalPhoto;

    @JSONField(name = "State")
    private Integer state;

    @JSONField(name = "Flag")
    private Integer flag;

    @JSONField(name = "UpdateTime")
    private long updateTime;

    public CompanyRequest(String ipcType, String requestUrl) {
        this.ipcType = ipcType;
        this.requestUrl = requestUrl;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getEconomicType() {
        return economicType;
    }

    public void setEconomicType(String economicType) {
        this.economicType = economicType;
    }

    public String getRegCapital() {
        return regCapital;
    }

    public void setRegCapital(String regCapital) {
        this.regCapital = regCapital;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getLegalId() {
        return legalId;
    }

    public void setLegalId(String legalId) {
        this.legalId = legalId;
    }

    public String getLegalPhone() {
        return legalPhone;
    }

    public void setLegalPhone(String legalPhone) {
        this.legalPhone = legalPhone;
    }

    public String getLegalPhoto() {
        return legalPhoto;
    }

    public void setLegalPhoto(String legalPhoto) {
        this.legalPhoto = legalPhoto;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public void setRequestUrl(String requestUrl) {
        super.requestUrl = requestUrl;
    }

}
