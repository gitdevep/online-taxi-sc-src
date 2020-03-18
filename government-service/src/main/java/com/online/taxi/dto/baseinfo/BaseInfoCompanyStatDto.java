package com.online.taxi.dto.baseinfo;

/**
 * 网约车平台运营规模信息
 *
 * @date 2018/8/30
 */
public class BaseInfoCompanyStatDto {
    /**
     * 平台注册网约车数量
     */
    private Integer vehicleNum;
    /**
     * 平台注册驾驶员数量
     */
    private Integer driverNum;

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
}
