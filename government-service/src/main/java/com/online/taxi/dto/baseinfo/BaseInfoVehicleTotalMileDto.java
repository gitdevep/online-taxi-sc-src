package com.online.taxi.dto.baseinfo;

import lombok.Data;

/**
 * 网约车车辆里程信息
 *
 * @date 2018/9/11
 */
@Data
public class BaseInfoVehicleTotalMileDto {
    /**
     * PK
     */
    private Integer id;

    /**
     * 车辆号牌
     */
    private String plateNumber;

    /**
     * 行驶总里程
     */
    private Integer totalMile;
}
