package com.online.taxi.service;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.request.OrderDtoRequest;

/**
 * 功能描述
 *
 * @date 2018/8/25
 */

public interface OrderGrabService {

    /**
     * 司机抢单
     * @param orderRequest
     * @return
     * @throws Exception
     */
    ResponseResult grab(OrderDtoRequest orderRequest) throws Exception;
}
