package com.online.taxi.entity;

import lombok.Data;

import java.util.Date;

/**
 */
@Data
public class PassengerWalletRecord {
    private Integer id;

    private Integer passengerInfoId;

    private String transactionId;

    private Date payTime;

    private Double payCapital;

    private Double payGiveFee;

    private Double refundCapital;

    private Double refundGiveFee;

    private Double rechargeDiscount;

    private Integer payType;

    private Integer payStatus;

    private Integer tradeType;

    private String tradeReason;

    private String description;

    private String createUser;

    private Integer orderId;

    private Date createTime;

    private Date updateTime;

}