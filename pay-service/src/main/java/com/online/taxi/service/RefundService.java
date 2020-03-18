package com.online.taxi.service;

import com.online.taxi.dto.ResponseResult;

/**
 * @date 2018/8/21
 */
public interface RefundService {

    ResponseResult refund(Integer yid , Integer orderId, Double refundPrice,String createUser);
}
