package com.online.taxi.request;

import lombok.Data;

/**
 * 订单
 *
 **/
@Data
public class OrderServerOrderRequest {

    private Integer orderId;

    private String orderNumber;
    private String driverPhone;
    /**
     * 城市
     */
    private String city;

    /**
     * 渠道
     */
    private Integer channel;

    /**
     * 车辆级别
     */
    private Integer carGrade;

    private Integer passengerId;
    /**
     * 乘客手机号
     */
    private String passengerPhoneNumber;
    /**
     * 起始经度
     */
    private String startLongitude;
    /**
     * 起始纬度
     */
    private String startLatitude;
    /**
     * 起始点
     */
    private String startAddress;

    private String endLongitude;

    private String endLatitude;

    private String endAddress;

    private int orderType;

    private String otherName;

    private String otherPhone;

    private String userLongitude;

    private String userLatitude;
    /**
     * 订单开始时间
     */
    private Long orderStartTime;

    /**
     * 0是预估订单 1是实时订单
     */
    private int serviceType;
    /**
     * 设备来源 1: ios 2:android
     */
    private int source;
    /**
     * 状态
     */
    private int status;
    /**
     * 司机ID
     */
    private Integer driverId;

    /**
     * 司机状态
     */
    private int driverStatus;
    /**
     * 是否使用优惠券
     */
    private int useCoupon;

    /**
     * 接到乘客经度
     */
    private String receivePassengerLongitude;
    /**
     * 接到乘客纬度
     */
    private String receivePassengerLatitude;
    /**
     * 乘客下车经度
     */
    private String passengerGetoffLongitude;
    /**
     * 乘客下车纬度
     */
    private String passengerGetoffLatitude;
    /**
     * 司机去接乘客出发时间
     */
    private Long driverStartTime;
    /**
     * 司机到达时间
     */
    private Long driverArrivedTime;
    /**
     * 接到乘客时间
     */
    private Long receivePassengerTime;
    /**
     * 乘客下车时间
     */
    private Long passengerGetoffTime;
    /**
     * 是否评价
     */
    private int isEvaluate;
    /**
     * 是否通知客服
     */
    private int isAnnotate;
    /**
     * 发票状态
     */
    private int invoiceType;
    /**
     * 是否支付
     */
    private int isPaid;
    /**
     * 是否取消订单 0：未取消   1：已取消
     */
    private int isCancel;
    /**
     * 是否调帐 0：未调帐  1：已调帐
     */
    private int isAdjust;
    /**
     * 是否有异议 0：没有  1：有
     */
    private int isDissent;
    /**
     * 取消订单类型id
     */
    private int cancelOrderType;
}
