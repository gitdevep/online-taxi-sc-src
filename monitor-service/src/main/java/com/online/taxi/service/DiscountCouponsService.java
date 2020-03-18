package com.online.taxi.service;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.request.DiscountCouponsRequest;

/**
 * 功能描述
 *
 * @date 2018/10/17
 */
public interface DiscountCouponsService {

    /**
     * 优惠券统计
     * @param request
     * @return
     */
    ResponseResult DiscountCouponsStatistics(DiscountCouponsRequest request);
}
