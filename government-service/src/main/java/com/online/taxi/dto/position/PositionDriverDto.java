package com.online.taxi.dto.position;

import lombok.Data;

/**
 * 驾驶员定位信息
 *
 * @date 2018/8/31
 */
@Data
public class PositionDriverDto {

    private Integer id;
    /**
     * 司机id
     */
    private Integer driverId;
    /**
     * 车辆id
     */
    private Integer carId;
    /**
     * 轨迹点
     */
    private String points;
    /**
     * 车牌号
     */
    private String plateNumber;
    /**
     * 城市编码
     */
    private String cityCode;
    /**
     * 驾驶证号
     */
    private String drivingLicenceNumber;
    /**
     * 订单号
     */
    private String orderNumber;
}
