package com.online.taxi.dto.order;

import lombok.Data;

import java.util.Date;

/**
 * 订单DTO
 *
 * @date 2018/8/28
 */
@Data
public class OrderDto {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 预计用车时间
     */
    private Date orderStartTime;

    /**
     * 订单发起时间
     */
    private Date startTime;

    /**
     * 预计出发详细地址
     */
    private String startAddress;

    /**
     * 预计出现地点经度
     */
    private String startLongitude;

    /**
     * 预计出发地点维度
     */
    private String startLatitude;

    /**
     * 预计目的地
     */
    private String endAddress;

    /**
     * 预计目的地经度
     */
    private String endLongitude;

    /**
     * 预计目的地经度
     */
    private String endLatitude;

    /**
     * 司机ID
     */
    private Integer driverId;

    /**
     * 机动车驾驶证编号
     */
    private String drivingLicenceNumber;

    /**
     * 车辆号码
     */
    private String plateNumber;

    /**
     * 司机电话号码
     */
    private String driverPhone;

    /**
     * 派单成功时间
     */
    private Date driverGrabTime;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 订单是否取消
     * 0：未取消
     * 1：已取消
     */
    private Integer isCancel;

    /**
     * 运价规则
     */
    private String ruleId;
}
