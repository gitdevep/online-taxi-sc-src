package com.online.taxi.mapper;

import com.online.taxi.entity.PassengerWalletRecord;

import java.util.List;

/**
 */
public interface PassengerWalletRecordMapper {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(PassengerWalletRecord record);

    PassengerWalletRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PassengerWalletRecord record);

    List<PassengerWalletRecord> selectPaidRecordByOrderId(Integer orderId);
}