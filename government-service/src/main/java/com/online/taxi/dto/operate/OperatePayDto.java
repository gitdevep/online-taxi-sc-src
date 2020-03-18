package com.online.taxi.dto.operate;

import lombok.Data;

import java.util.Date;

/**
 * 经营支付
 *
 * @date 2018/8/31
 */
@Data
public class OperatePayDto {
    /**
     * PK
     */
    private Integer orderId;

    /**
     * 订单id
     */
    private String orderNumber;

    /**
     * 上车位置行政区划编号
     */
    private Integer cityCode;

    /**
     * 机动车驾驶证号
     */
    private String drivingLicenceNumber;

    /**
     * 运价类型编号
     */
    private String ruleId;

    /**
     * 车辆号牌
     */
    private String plateNumber;

    /**
     * 预计上车时间
     */
    private Date orderStartTime;

    /**
     * 车辆出发经度
     */
    private String receivePassengerLongitude;

    /**
     * 车辆出发纬度
     */
    private String receivePassengerLatitude;

    /**
     * 上车时间
     */
    private Date receivePassengerTime;

    /**
     * 车辆到达经度
     */
    private String passengerGetoffLongitude;

    /**
     * 车辆到达纬度
     */
    private String passengerGetoffLatitude;

    /**
     * 下车时间
     */
    private Date passengerGetoffTime;

    /**
     * 载客里程
     */
    private Double totalDistance;

    /**
     * 载客时间
     */
    private Double totalTime;

    /**
     * 实收金额
     */
    private Double totalPrice;

    /**
     * 远途加价金额
     */
    private Double beyondPrice;

    /**
     * 发票状态，0未开票，1已开票，2未知
     */
    private Integer invoiceType;

    /**
     * 过路费
     */
    private Double roadPrice;
    /**
     * 停车费
     */
    private Double parkingPrice;

    /**
     * 其它费
     */
    private Double otherPrice;

}
