package com.online.taxi.service;

import java.util.List;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.entity.PassengerAddress;

/**
 * @date 2018/08/15
 **/
public interface PassengerAddressService  {

    int deleteByPrimaryKey(Integer id);

    int insert(PassengerAddress record);

    int insertSelective(PassengerAddress record);

    PassengerAddress selectByPrimaryKey(Integer id);

    List<PassengerAddress> selectPassengerAddressList(Integer PassengerInfoId);

    PassengerAddress selectByAddPassengerInfoId(PassengerAddress passengerInfoId);

    int updateByPrimaryKeySelective(PassengerAddress record);

    ResponseResult updatePassengerAddress(PassengerAddress record);

    int updateByPrimaryKey(PassengerAddress record);
}
