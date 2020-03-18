package com.online.taxi.service;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.request.CarRequest;
import com.online.taxi.response.CarAmountResponse;

/**
 * 车辆统计
 *
 * @date 2018/10/17
 */

public interface CarAmountService {

    /**
     * 车辆统计
     * @param request
     * @return
     */
    ResponseResult carAmount(CarRequest request);
}
