package com.online.taxi.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.annotation.JSONField;

public class VehiclelnsuranceRequest extends BaseMPRequest {

    @JSONField(name = "VehicleNo")
    private String vehicleNo;

    @JSONField(name = "InsurCom")
    private String insurCom;

    @JSONField(name = "InsurNum")
    private String insurNum;

    @JSONField(name = "InsurType")
    private String insurType;

    @JSONField(name = "InsurCount")
    private Integer insurCount;

    @JSONField(name = "InsurEff")
    private Long insurEff;

    @JSONField(name = "InsurExp")
    private Long insurExp;

    @JSONField(name = "Flag")
    private Integer flag;

    @JSONField(name = "UpdateTime")
    private Long updateTime;

//    @JSONField(name = "CompanyId")
//    private String companyId;

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getInsurCom() {
        return insurCom;
    }

    public void setInsurCom(String insurCom) {
        this.insurCom = insurCom;
    }

    public String getInsurNum() {
        return insurNum;
    }

    public void setInsurNum(String insurNum) {
        this.insurNum = insurNum;
    }

    public String getInsurType() {
        return insurType;
    }

    public void setInsurType(String insurType) {
        this.insurType = insurType;
    }

    public Integer getInsurCount() {
        return insurCount;
    }

    public void setInsurCount(Integer insurCount) {
        this.insurCount = insurCount;
    }

    public Long getInsurEff() {
        return insurEff;
    }

    public void setInsurEff(Long insurEff) {
        this.insurEff = insurEff;
    }

    public Long getInsurExp() {
        return insurExp;
    }

    public void setInsurExp(Long insurExp) {
        this.insurExp = insurExp;
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
    public String getCompanyId() {
        return companyId;
    }

    @Override
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public VehiclelnsuranceRequest() {
    }

    public VehiclelnsuranceRequest(String ipcType, String requestUrl) {
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
