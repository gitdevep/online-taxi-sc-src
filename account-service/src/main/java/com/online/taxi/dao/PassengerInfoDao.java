package com.online.taxi.dao;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.online.taxi.entity.PassengerInfo;
import com.online.taxi.entity.PassengerRegisterSource;
import com.online.taxi.mapper.PassengerInfoMapper;
import com.online.taxi.mapper.PassengerRegisterSourceMapper;

import java.util.List;

/**
 * 乘客端
 *
 * @date 2018/08/08
 **/
@Repository
@RequiredArgsConstructor
public class PassengerInfoDao {

    @NonNull
    private PassengerInfoMapper passengerInfoDao;

    @NonNull
    private PassengerRegisterSourceMapper passengerRegisterSourceMapper;

    public int insertPassengerRegisterSource(PassengerRegisterSource passengerRegisterSource) {
        return passengerRegisterSourceMapper.insertSelective(passengerRegisterSource);
    }

    public PassengerInfo queryPassengerInfoById(Integer passengerId) {
        return passengerInfoDao.selectByPrimaryKey(passengerId);
    }

    public PassengerInfo queryPassengerInfoByPhoneNum(String phoneNum) {
        return passengerInfoDao.queryPassengerInfoByPhoneNum(phoneNum);
    }

    public int deleteByPrimaryKey(Integer id) {
        return passengerInfoDao.deleteByPrimaryKey(id);
    }

    public int insert(PassengerInfo record) {
        return passengerInfoDao.insert(record);
    }

    public int insertSelective(PassengerInfo record) {
        return passengerInfoDao.insertSelective(record);
    }

    public PassengerInfo selectByPrimaryKey(Integer id) {
        return passengerInfoDao.selectByPrimaryKey(id);
    }
    public List<PassengerInfo> selectByPrimaryKeyList() {
        return passengerInfoDao.selectByPrimaryKeyList();
    }

    public int updateByPrimaryKeySelective(PassengerInfo record) {
        return passengerInfoDao.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(PassengerInfo record) {
        return passengerInfoDao.updateByPrimaryKey(record);
    }

    public void updatePassengerInfoLoginTime(Integer passengerId) {
        passengerInfoDao.updatePassengerInfoLoginTime(passengerId);
    }
}
