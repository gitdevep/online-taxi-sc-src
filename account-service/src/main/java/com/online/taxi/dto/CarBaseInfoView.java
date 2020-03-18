package com.online.taxi.dto;

import com.online.taxi.entity.CarBaseInfo;
import com.online.taxi.entity.CarInfo;

import lombok.Data;

/**
 * 车辆信息
 * @date 2018-08-09
 **/
@Data
public class CarBaseInfoView {

    private CarBaseInfo carBaseInfo;

    private  CarInfo carInfo;

}
