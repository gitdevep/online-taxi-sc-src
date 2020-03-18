package com.online.taxi.dto.baseinfo;

import lombok.Data;

import java.util.Date;

/**
 * 车辆基本信息DTO
 *
 * @date 2018/8/29
 **/
@Data
public class BaseInfoVehicleDto {
    /**
     * PK
     */
    private Integer id;

    /**
     * 车辆号牌
     */
    private String plateNumber;

    /**
     * 车辆颜色
     */
    private String plateColor;

    /**
     * 核定载客位
     */
    private Integer seats;

    /**
     * 车辆厂牌
     */
    private String brand;

    /**
     * 车辆型号
     */
    private String model;

    /**
     * 车辆类型
     */
    private String carBaseType;

    /**
     * 车辆所有人
     */
    private String carOwner;

    /**
     * 车身颜色
     */
    private String color;

    /**
     * 发动机号
     */
    private String engineNumber;

    /**
     * 车辆VIN码
     */
    private String vinNumber;

    /**
     * 车辆注册日期
     */
    private Date registerTime;

    /**
     * 车辆燃料类型
     */
    private String fuelType;

    /**
     * 发动机排量
     */
    private String engineCapacity;

    /**
     * 车辆运输证发证机构
     */
    private String transportIssuingAuthority;

    /**
     * 车辆经营区域
     */
    private String businessArea;

    /**
     * 车辆运输证有效期起
     */
    private Date transportCertificateValidityStart;

    /**
     * 车辆运输证有效期止
     */
    private Date transportCertificateValidityEnd;

    /**
     * 车辆初次登记日期
     */
    private Date firstRegisterTime;

    /**
     * 车辆检修状态
     */
    private String stateOfRepair;

    /**
     * 车辆年度审验状态
     */
    private String annualAuditStatus;

    /**
     * 发票打印设备序列号
     */
    private String invoicePrintingEquipmentNumber;

    /**
     * 卫星定位设备品牌
     */
    private String gpsBrand;

    /**
     * 卫星定位设备型号
     */
    private String gpsModel;

    /**
     * 卫星定位设备IMEI号
     */
    private Date gpsInstallTime;

    /**
     * 报备日期
     */
    private Date reportTime;

    /**
     * 服务类型
     */
    private String serviceType;

    /**
     * 运价类型编码
     */
    private String chargeTypeCode;

    /**
     * 状态
     */
    private Integer useStatus;
}
