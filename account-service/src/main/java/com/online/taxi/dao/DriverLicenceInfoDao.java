package com.online.taxi.dao;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.online.taxi.entity.DriverLicenceInfo;
import com.online.taxi.mapper.DriverLicenceInfoMapper;

/**
 *	审核资料
 * @date 2018/08/10
 **/
@Repository
@RequiredArgsConstructor
public class DriverLicenceInfoDao {

    @NonNull
    private DriverLicenceInfoMapper driverLicenceInfoMapper;

    public int deleteByPrimaryKey(Integer id) {
        return driverLicenceInfoMapper.deleteByPrimaryKey(id);
    }


    public int insert(DriverLicenceInfo record) {
        return driverLicenceInfoMapper.insert(record);
    }


    public int insertSelective(DriverLicenceInfo record) {
        return driverLicenceInfoMapper.insertSelective(record);
    }


    public DriverLicenceInfo selectByPrimaryKey(Integer id) {
        return driverLicenceInfoMapper.selectByPrimaryKey(id);
    }

    public DriverLicenceInfo selectByDriverId(Integer driverId) {
        return driverLicenceInfoMapper.selectByDriverId(driverId);
    }

    public int updateByPrimaryKeySelective(DriverLicenceInfo record) {
        return driverLicenceInfoMapper.updateByPrimaryKeySelective(record);
    }
    public int updateByDriverIdSelective(DriverLicenceInfo record) {
        return driverLicenceInfoMapper.updateByDriverIdSelective(record);
    }

    public int updateByPrimaryKey(DriverLicenceInfo record) {
        return driverLicenceInfoMapper.updateByPrimaryKey(record);
    }
}
