package com.online.taxi.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.online.taxi.dao.CarInfoDao;
import com.online.taxi.entity.CarInfo;
import com.online.taxi.service.CarInfoService;

import java.util.Map;

/**
 * 车辆服务
 * @date 2018/08/15
 **/
@Service
@RequiredArgsConstructor
public class CarInfoServiceImpl implements CarInfoService {

    @NonNull
    private CarInfoDao carInfoDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return carInfoDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CarInfo record) {
        return carInfoDao.insert(record);
    }

    @Override
    public int insertSelective(CarInfo record) {
        return carInfoDao.insertSelective(record);
    }

    @Override
    public CarInfo selectByPrimaryKey(Integer id) {
        return carInfoDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CarInfo record) {
        return carInfoDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CarInfo record) {
        return carInfoDao.updateByPrimaryKey(record);
    }

    @Override
    public int countCarInfo(Map<String, Object> param) {
        return carInfoDao.countCarInfo(param);
    }
}
