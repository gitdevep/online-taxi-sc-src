package com.online.taxi.dto;

import com.online.taxi.entity.DriverBaseInfo;
import com.online.taxi.entity.DriverInfo;
import com.online.taxi.entity.DriverLicenceInfo;

import lombok.Data;

/**
 * 司机信息
 * @date 2018-08-09
 **/
@Data
public class DriverBaseInfoView {

    private DriverBaseInfo driverBaseInfo;

    private DriverInfo driverInfo;

    private DriverLicenceInfo driverLicenceInfo;


}
