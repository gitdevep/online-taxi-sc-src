package com.online.taxi.service;

import java.util.HashMap;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.entity.PassengerInfo;
import com.online.taxi.entity.PassengerRegisterSource;
import com.online.taxi.request.GetTokenRequest;

/**
 * @date 2018/08/15
 **/
public interface PassengerInfoService {

     PassengerInfo queryPassengerInfoByPhoneNum(String phoneNum);

     void insertPassengerInfo(PassengerInfo passengerInfo) ;

     void updatePassengerInfoLoginTime(Integer passengerId);

     HashMap<String, Object> getPassengerInfoView(GetTokenRequest getTokenRequest);

     ResponseResult updatePassengerInfo(PassengerInfo passengerInfo);

     int initPassengerWallet(Integer passengerId);

     PassengerInfo queryPassengerInfoById(Integer passengerId);

     int insertPassengerRegisterSource(PassengerRegisterSource passengerRegisterSource);

     int updatePassengerInfoById(PassengerInfo passengerInfo);
}
