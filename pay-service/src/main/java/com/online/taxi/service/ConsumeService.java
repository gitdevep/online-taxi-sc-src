package com.online.taxi.service;

import com.online.taxi.dto.ResponseResult;

/**
 * @date 2018/8/21
 */
public interface ConsumeService {

    ResponseResult freeze(Integer yid, Integer orderId, Double price,Double limitPrice);

    ResponseResult unFreeze(Integer yid, Integer orderId);

    ResponseResult pay(Integer yid, Integer orderId, Double price,Double tailPrice,Double replenishPrice );
}
