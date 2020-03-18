package com.online.taxi.service;

import java.util.HashMap;

import com.online.taxi.dto.DriverBaseInfoView;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.entity.DriverInfo;
import com.online.taxi.request.UpdateDriverAddressRequest;

/**
 * 司机信息服务
 * @date 2018/08/15
 **/
public interface DriverInfoService {

     DriverInfo queryDriverInfoByPhoneNum(String phoneNum);

     int updateDriverInfoByPhoneNum(DriverInfo driverInfo);

     ResponseResult changeDriverBaseInfo(DriverBaseInfoView view, int type);

     HashMap<String, Object> getDriverBaseInfoView(int driverId);

     ResponseResult updateDriverAddressRequest(UpdateDriverAddressRequest request);

     int updateByPrimaryKeySelective(DriverInfo record);

     int updateByPrimaryKey(DriverInfo record);

     int updateCarIdById(DriverInfo record);
}
