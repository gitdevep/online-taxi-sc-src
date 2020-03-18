package com.online.taxi.service;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.request.OrderRequest;

/**
 * 功能描述
 *
 * @date 2018/10/16
 */
public interface OrderStatisticsService {

    /**
     * 用户统计
     * @param request
     * @return
     */
    ResponseResult orderStatistics(OrderRequest request);

}
