package com.online.taxi.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.annotation.JSONField;

public class VehicleTotalMileRequest extends BaseMPRequest {

    @JSONField(name = "Address")
    private Integer address;

    @JSONField(name = "VehicleNo")
    private String vehicleNo;

    @JSONField(name = "TotalMile")
    private Integer totalMile;

    @JSONField(name = "Flag")
    private Integer flag;

    @JSONField(name = "UpdateTime")
    private Long updateTime;

//    @JSONField(name = "CompanyId")
//    private String companyId;

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public Integer getTotalMile() {
        return totalMile;
    }

    public void setTotalMile(Integer totalMile) {
        this.totalMile = totalMile;
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

    public VehicleTotalMileRequest() {
    }

    public VehicleTotalMileRequest(String ipcType, String requestUrl) {
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
