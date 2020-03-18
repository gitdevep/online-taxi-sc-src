package com.online.taxi.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import com.online.taxi.entity.DriverBaseInfo;

@Mapper
@Service
public interface DriverBaseInfoMapper {

    DriverBaseInfo selectByPrimaryKey(Integer id);

}
