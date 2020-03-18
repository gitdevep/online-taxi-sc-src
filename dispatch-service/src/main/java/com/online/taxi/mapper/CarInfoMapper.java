package com.online.taxi.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import com.online.taxi.entity.CarInfo;

@Mapper
@Service
public interface CarInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarInfo record);

    int insertSelective(CarInfo record);

    CarInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarInfo record);

    int updateByPrimaryKey(CarInfo record);
}
