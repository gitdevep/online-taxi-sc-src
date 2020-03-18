package com.online.taxi.mapper;

import org.springframework.stereotype.Service;

import com.online.taxi.entity.CarInfo;

import java.util.Map;

/**
 * @date 2018/08/10
 */
@Service
public interface CarInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(CarInfo record);

    int insertSelective(CarInfo record);

    CarInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarInfo record);

    int updateByPrimaryKey(CarInfo record);

    int countCarInfo(Map<String, Object> param);
}
