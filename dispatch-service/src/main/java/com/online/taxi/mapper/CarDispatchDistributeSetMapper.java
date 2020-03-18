package com.online.taxi.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.online.taxi.entity.CarDispatchDistributeSet;

@Mapper
public interface CarDispatchDistributeSetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarDispatchDistributeSet record);

    int insertSelective(CarDispatchDistributeSet record);

    CarDispatchDistributeSet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarDispatchDistributeSet record);

    int updateByPrimaryKey(CarDispatchDistributeSet record);

    CarDispatchDistributeSet getOpenedByCityCode(@Param("cityCode") String cityCode);
}
