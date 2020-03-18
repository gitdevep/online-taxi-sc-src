package com.online.taxi.service.impl;

import com.online.taxi.entity.DriverInfo;
import com.online.taxi.mapper.DriverInfoMapper;
import com.online.taxi.service.DriverService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能描述
 *
 * @date 2018/8/28
 */
@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverInfoMapper driverInfoMapper;

    @Override
    public List<DriverInfo> selectDriverList() {
        return driverInfoMapper.selectDriverInfoList();
    }
}
