package com.online.taxi.service;

import com.online.taxi.dto.CarBaseInfoView;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.entity.CarBaseInfo;

/**
 * 车辆服务
 *
 * @date 2018/08/15
 * */
public interface CarBaseService {

     int updateCarBaseInfoView(CarBaseInfo view);

     ResponseResult addCarBaseInfo(CarBaseInfoView view);

}
