package com.online.taxi.mapper;

import org.springframework.stereotype.Service;

import com.online.taxi.entity.DriverBaseInfo;

/**
 * @date 2018/08/10
 */
@Service
public interface DriverBaseInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(DriverBaseInfo record);

    int insertSelective(DriverBaseInfo record);

    DriverBaseInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DriverBaseInfo record);

    int updateByPrimaryKey(DriverBaseInfo record);
}
