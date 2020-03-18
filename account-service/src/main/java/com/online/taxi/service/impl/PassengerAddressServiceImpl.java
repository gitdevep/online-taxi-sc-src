package com.online.taxi.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.online.taxi.constatnt.BusinessInterfaceStatus;
import com.online.taxi.dao.PassengerAddressDao;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.entity.PassengerAddress;
import com.online.taxi.service.PassengerAddressService;

import java.util.Date;
import java.util.List;

/**
 * @date 2018/08/15
 **/
@Service
@RequiredArgsConstructor
public class PassengerAddressServiceImpl implements PassengerAddressService {

    @NonNull
    private PassengerAddressDao passengerAddressDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return passengerAddressDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(PassengerAddress record) {
        return passengerAddressDao.insert(record);
    }

    @Override
    public int insertSelective(PassengerAddress record) {
        return passengerAddressDao.insertSelective(record);
    }

    @Override
    public PassengerAddress selectByPrimaryKey(Integer id) {
        return passengerAddressDao.selectByPrimaryKey(id);
    }

    @Override
    public List<PassengerAddress> selectPassengerAddressList(Integer passengerInfoId) {
        return passengerAddressDao.selectPassengerAddressList(passengerInfoId);
    }

    @Override
    public PassengerAddress selectByAddPassengerInfoId(PassengerAddress passengerInfoId) {
        return passengerAddressDao.selectByAddPassengerInfoId(passengerInfoId);
    }

    @Override
    public int updateByPrimaryKeySelective(PassengerAddress record) {
        return passengerAddressDao.updateByPrimaryKeySelective(record);
    }

    /**
     * 修改司机家庭地址
     * @param passengerAddress 对象
     * @return ResponseResult实例
     */
    @Override
    public ResponseResult updatePassengerAddress(PassengerAddress passengerAddress) {
        int updateOrInsert;
        if (null != passengerAddress.getPassengerInfoId()) {
            PassengerAddress passengerAddressTemp = passengerAddressDao.selectByAddPassengerInfoId(passengerAddress);
            if (null == passengerAddressTemp) {
                passengerAddress.setCreateTime(new Date());
                updateOrInsert = passengerAddressDao.insertSelective(passengerAddress);
            } else {
                updateOrInsert = passengerAddressDao.updatePassengerAddress(passengerAddress);
            }
        } else {
            passengerAddress.setCreateTime(new Date());
            updateOrInsert = passengerAddressDao.insertSelective(passengerAddress);
        }
        if (0 == updateOrInsert) {
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(),"修改或添加乘客地址信息失败!");
        } else {
            return ResponseResult.success("");
        }
    }

    @Override
    public int updateByPrimaryKey(PassengerAddress record) {
        return passengerAddressDao.updateByPrimaryKey(record);
    }
}
