package com.online.taxi.service;

import com.online.taxi.entity.PassengerWallet;
import com.online.taxi.entity.PassengerWalletRecord;

/**
 * @date 2018/8/21
 */
public interface PassengerWalletService {

    /**
     * 用户的余额变更
     * @param yid
     * @param capital
     * @param giveFee
     * @param changeStatus -1：减，1：加
     * @return
     */
    int alterPassengerWalletPrice(Integer yid,Double capital,Double giveFee,int changeStatus);

    /**
     * 生成钱包记录
     * @param yid
     * @param capital
     * @param giveFee
     * @param payType
     * @param tradeType
     * @param description
     * @param orderId
     * @return
     */
    PassengerWalletRecord createWalletRecord(Integer yid , Double capital, Double giveFee,
                                             Integer payType, Integer tradeType , String description ,
                                             Integer orderId , Integer payStatus,String createUser);

    /**
     * 支付完成处理逻辑
     * @param rechargeType
     * @param rechargeId
     * @return
     */
    int handleCallBack(int rechargeType,Integer rechargeId,String tradeNo);

    /**
     * 初始化乘客钱包
     * @param passengerInfoId
     * @param capital
     * @param giveFee
     * @return
     */
    PassengerWallet initPassengerWallet(Integer passengerInfoId, Double capital, Double giveFee);

    public int unfreezeWallet(Integer yid, Double freezeCapital, Double freezeGiveFee);
}
