package com.online.taxi.mapper;

import org.springframework.stereotype.Service;

import com.online.taxi.entity.DriverInfo;

import java.util.List;

/**
 * @date 2018/08/10
 */
@Service
public interface DriverInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(DriverInfo record);

    int insertSelective(DriverInfo record);

    DriverInfo selectByPrimaryKey(Integer id);

    List<DriverInfo> selectByPrimaryKeyList();

    int updateByPrimaryKeySelective(DriverInfo record);

    int updateByPrimaryKey(DriverInfo record);

    int updateCarIdById(DriverInfo record);

    DriverInfo queryDriverInfoByPhoneNum(String phoneNum);

    DriverInfo queryDriverInfoByCarId(Integer carId);

    int updateDriverInfoByPhoneNum(DriverInfo driverInfo);

}
