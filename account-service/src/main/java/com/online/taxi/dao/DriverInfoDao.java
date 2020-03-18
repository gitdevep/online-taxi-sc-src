package com.online.taxi.dao;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.online.taxi.entity.DriverInfo;
import com.online.taxi.mapper.DriverInfoMapper;

import java.util.List;

/**
 *  司机端
 * @date  2018/08/08
 **/

@Repository
@RequiredArgsConstructor
public class DriverInfoDao   {

    @NonNull
    private  DriverInfoMapper driverInfoMapper;

    public int deleteByPrimaryKey(Integer id) {
        return driverInfoMapper.deleteByPrimaryKey(id);
    }

    public int insert(DriverInfo record) {
        return driverInfoMapper.insert(record);
    }

    public int insertSelective(DriverInfo record) {
        return driverInfoMapper.insertSelective(record);
    }

    public DriverInfo selectByPrimaryKey(Integer id) {
        return driverInfoMapper.selectByPrimaryKey(id);
    }
    public List<DriverInfo> selectByPrimaryKeyList() {
        return driverInfoMapper.selectByPrimaryKeyList();
    }

    public int updateByPrimaryKeySelective(DriverInfo record) {
        return driverInfoMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(DriverInfo record) {
        return driverInfoMapper.updateByPrimaryKey(record);
    }
    public int updateCarIdById(DriverInfo record) {
        return driverInfoMapper.updateCarIdById(record);
    }

    public DriverInfo queryDriverInfoByPhoneNum(String phoneNum) {
        return driverInfoMapper.queryDriverInfoByPhoneNum(phoneNum);
    }


    public int updateDriverInfoByPhoneNum(DriverInfo driverInfo) {
       return  driverInfoMapper.updateDriverInfoByPhoneNum(driverInfo);
    }

    public DriverInfo queryDriverInfoByCarId(Integer carId){
        return  driverInfoMapper.queryDriverInfoByCarId(carId);
    }
}
