package com.online.taxi.dao;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.online.taxi.entity.CarBaseInfo;
import com.online.taxi.mapper.CarBaseInfoMapper;

/**
 * @date 2018/08/10
 **/
@Repository
@RequiredArgsConstructor
public class CarBaseInfoDao  {

    @NonNull
    private CarBaseInfoMapper carBaseInfoMapper;

    public int deleteByPrimaryKey(Integer id) {
        return carBaseInfoMapper.deleteByPrimaryKey(id);
    }

    public int insert(CarBaseInfo record) {
        return carBaseInfoMapper.insert(record);
    }

    public int insertSelective(CarBaseInfo record) {
        return carBaseInfoMapper.insertSelective(record);
    }

    public CarBaseInfo selectByPrimaryKey(Integer id) {
        return carBaseInfoMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(CarBaseInfo record) {
        return carBaseInfoMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(CarBaseInfo record) {
        return carBaseInfoMapper.updateByPrimaryKey(record);
    }
}
