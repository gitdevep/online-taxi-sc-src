package com.online.taxi.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.annotation.JSONField;

public class DriverRequest extends BaseMPRequest {

    @JSONField(name = "Address")
    private Integer address;

    @JSONField(name = "DriverPhone")
    private String driverPhone;

    @JSONField(name = "DriverGender")
    private String driverGender;

    @JSONField(name = "DriverBirthday")
    private Long driverBirthday;

    @JSONField(name = "DriverNation")
    private String driverNation;

    @JSONField(name = "DriverContactAddress")
    private String driverContactAddress;

    @JSONField(name = "LicenseId")
    private String licenseId;

    @JSONField(name = "GetDriverLicenseDate")
    private Long getDriverLicenseDate;

    @JSONField(name = "DriverLicenseOn")
    private Long driverLicenseOn;

    @JSONField(name = "DriverLicenseOff")
    private Long driverLicenseOff;

    @JSONField(name = "TaxiDriver")
    private Integer taxiDriver;

    @JSONField(name = "CertificateNo")
    private String certificateNo;

    @JSONField(name = "NetworkCarIssueOrganization")
    private String networkCarIssueOrganization;

    @JSONField(name = "NetworkCarIssueDate")
    private Long networkCarIssueDate;

    @JSONField(name = "GetNetworkCarProofDate")
    private Long getNetworkCarProofDate;

    @JSONField(name = "NetworkCarProofOn")
    private Long networkCarProofOn;

    @JSONField(name = "NetworkCarProofOff")
    private Long networkCarProofOff;

    @JSONField(name = "RegisterDate")
    private Long registerDate;

    @JSONField(name = "CommercialType")
    private Integer commercialType;

    @JSONField(name = "ContractCompany")
    private String contractCompany;

    @JSONField(name = "ContractOn")
    private Long contractOn;

    @JSONField(name = "ContractOff")
    private Long contractOff;

    @JSONField(name = "State")
    private Integer state;

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

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getDriverGender() {
        return driverGender;
    }

    public void setDriverGender(String driverGender) {
        this.driverGender = driverGender;
    }

    public Long getDriverBirthday() {
        return driverBirthday;
    }

    public void setDriverBirthday(Long driverBirthday) {
        this.driverBirthday = driverBirthday;
    }

    public String getDriverNation() {
        return driverNation;
    }

    public void setDriverNation(String driverNation) {
        this.driverNation = driverNation;
    }

    public String getDriverContactAddress() {
        return driverContactAddress;
    }

    public void setDriverContactAddress(String driverContactAddress) {
        this.driverContactAddress = driverContactAddress;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public Long getGetDriverLicenseDate() {
        return getDriverLicenseDate;
    }

    public void setGetDriverLicenseDate(Long getDriverLicenseDate) {
        this.getDriverLicenseDate = getDriverLicenseDate;
    }

    public Long getDriverLicenseOn() {
        return driverLicenseOn;
    }

    public void setDriverLicenseOn(Long driverLicenseOn) {
        this.driverLicenseOn = driverLicenseOn;
    }

    public Long getDriverLicenseOff() {
        return driverLicenseOff;
    }

    public void setDriverLicenseOff(Long driverLicenseOff) {
        this.driverLicenseOff = driverLicenseOff;
    }

    public Integer getTaxiDriver() {
        return taxiDriver;
    }

    public void setTaxiDriver(Integer taxiDriver) {
        this.taxiDriver = taxiDriver;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getNetworkCarIssueOrganization() {
        return networkCarIssueOrganization;
    }

    public void setNetworkCarIssueOrganization(String networkCarIssueOrganization) {
        this.networkCarIssueOrganization = networkCarIssueOrganization;
    }

    public Long getNetworkCarIssueDate() {
        return networkCarIssueDate;
    }

    public void setNetworkCarIssueDate(Long networkCarIssueDate) {
        this.networkCarIssueDate = networkCarIssueDate;
    }

    public Long getGetNetworkCarProofDate() {
        return getNetworkCarProofDate;
    }

    public void setGetNetworkCarProofDate(Long getNetworkCarProofDate) {
        this.getNetworkCarProofDate = getNetworkCarProofDate;
    }

    public Long getNetworkCarProofOn() {
        return networkCarProofOn;
    }

    public void setNetworkCarProofOn(Long networkCarProofOn) {
        this.networkCarProofOn = networkCarProofOn;
    }

    public Long getNetworkCarProofOff() {
        return networkCarProofOff;
    }

    public void setNetworkCarProofOff(Long networkCarProofOff) {
        this.networkCarProofOff = networkCarProofOff;
    }

    public Long getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Long registerDate) {
        this.registerDate = registerDate;
    }

    public Integer getCommercialType() {
        return commercialType;
    }

    public void setCommercialType(Integer commercialType) {
        this.commercialType = commercialType;
    }

    public String getContractCompany() {
        return contractCompany;
    }

    public void setContractCompany(String contractCompany) {
        this.contractCompany = contractCompany;
    }

    public Long getContractOn() {
        return contractOn;
    }

    public void setContractOn(Long contractOn) {
        this.contractOn = contractOn;
    }

    public Long getContractOff() {
        return contractOff;
    }

    public void setContractOff(Long contractOff) {
        this.contractOff = contractOff;
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

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public DriverRequest() {
    }

    public DriverRequest(String ipcType, String requestUrl) {
        this.ipcType = ipcType;
        this.requestUrl = requestUrl;
    }

    @Override
    public void setRequestUrl(String requestUrl) {
        super.requestUrl = requestUrl;

    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
