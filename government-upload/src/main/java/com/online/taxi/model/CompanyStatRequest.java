package com.online.taxi.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * 
 * @description 报部：公司运营规模请求信息报文
 * @author jxl
 * @version
 * @date 2018年1月29日
 */
public class CompanyStatRequest extends BaseMPRequest {

    @JSONField(name = "VehicleNum")
    private Integer vehicleNum;

    @JSONField(name = "DriverNum")
    private Integer driverNum;

    @JSONField(name = "Flag")
    private Integer flag;

    @JSONField(name = "UpdateTime")
    private Long updateTime;

    public CompanyStatRequest(String ipcType, String requestUrl) {
        this.ipcType = ipcType;
        this.requestUrl = requestUrl;
    }

    public Integer getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(Integer vehicleNum) {
        this.vehicleNum = vehicleNum;
    }

    public Integer getDriverNum() {
        return driverNum;
    }

    public void setDriverNum(Integer driverNum) {
        this.driverNum = driverNum;
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
