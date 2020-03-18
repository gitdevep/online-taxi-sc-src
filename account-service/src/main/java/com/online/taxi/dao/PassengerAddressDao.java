package com.online.taxi.dao;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.online.taxi.entity.PassengerAddress;
import com.online.taxi.mapper.PassengerAddressMapper;

import java.util.List;

/**
 *  乘客地址
 * @date  2018/08/13
 **/
@Repository
@RequiredArgsConstructor
public class PassengerAddressDao {

    @NonNull
    private PassengerAddressMapper passengerAddressDao;

    public int deleteByPrimaryKey(Integer id) {
        return passengerAddressDao.deleteByPrimaryKey(id);
    }

    public int insert(PassengerAddress record) {
        return passengerAddressDao.insert(record);
    }

    public int insertSelective(PassengerAddress record) {
        return passengerAddressDao.insertSelective(record);
    }

    public PassengerAddress selectByPrimaryKey(Integer id) {
        return passengerAddressDao.selectByPrimaryKey(id);
    }

    public PassengerAddress selectByPassengerInfoId(Integer passengerInfoId) {
        return passengerAddressDao.selectByPassengerInfoId(passengerInfoId);
    }

    public List<PassengerAddress> selectPassengerAddressList(Integer passengerInfoId) {
        return passengerAddressDao.selectPassengerAddressList(passengerInfoId);
    }

    public PassengerAddress selectByAddPassengerInfoId(PassengerAddress passengerInfoId ) {
        return passengerAddressDao.selectByAddPassengerInfoId(passengerInfoId);
    }

    public int updateByPrimaryKeySelective(PassengerAddress record) {
        return passengerAddressDao.updateByPrimaryKeySelective(record);
    }

    public int updatePassengerAddress(PassengerAddress record) {
        return passengerAddressDao.updatePassengerAddress(record);
    }

    public int updateByPrimaryKey(PassengerAddress record) {
        return passengerAddressDao.updateByPrimaryKey(record);
    }
}
