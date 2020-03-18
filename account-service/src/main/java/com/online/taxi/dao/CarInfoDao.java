package com.online.taxi.dao;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.online.taxi.entity.CarInfo;
import com.online.taxi.mapper.CarInfoMapper;

import java.util.Map;

/**
  *汽车dao
 * @date 2018/08/10
 **/
@Repository
@RequiredArgsConstructor
public class CarInfoDao  {

    @NonNull
    private CarInfoMapper carInfoMapper;

    public int deleteByPrimaryKey(Integer id) {
        return carInfoMapper.deleteByPrimaryKey(id);
    }

    public int insert(CarInfo record) {
        return carInfoMapper.insert(record);
    }

    public int insertSelective(CarInfo record) {
        return carInfoMapper.insertSelective(record);
    }

    public CarInfo selectByPrimaryKey(Integer id) {
        return carInfoMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(CarInfo record) {
        return carInfoMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(CarInfo record) {
        return carInfoMapper.updateByPrimaryKey(record);
    }

    public int countCarInfo(Map<String, Object> param) {
        return carInfoMapper.countCarInfo(param);
    }

}
