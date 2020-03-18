package com.online.taxi.dto.baseinfo;

import lombok.Data;

import java.util.Date;

/**
 * @date 2018/8/30
 */
@Data
public class BaseInfoVehicleInsuranceDto {
    /**
     * PK
     */
    private Integer id;

    /**
     * 车辆号牌
     */
    private String plateNumber;

    /**
     * 保险公司名称
     */
    private String insuranceCompany;

    /**
     * 保险号
     */
    private String insuranceNumber;

    /**
     * 保险类型
     */
    private String insuranceType;

    /**
     * 保险金额
     */
    private Double insuranceCount;

    /**
     * 保险生效时间
     */
    private Date insuranceEff;

    /**
     * 保险到期时间
     */
    private Date insuranceExp;
}
