package com.online.taxi.controller;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.request.DiscountCouponsRequest;
import com.online.taxi.service.DiscountCouponsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 优惠券
 * @date 2018/10/15
 */
@RestController
@RequestMapping("/discount_coupons")
@Slf4j
public class DiscountCouponsController {

    @Autowired
    private DiscountCouponsService discountCouponsService;

    /**
     * 优惠券统计
     * @param request
     * @return
     */
    @PostMapping("/statistics")
    public ResponseResult discountCouponsStatistics(@RequestBody DiscountCouponsRequest request){
        return discountCouponsService.DiscountCouponsStatistics(request);
    }
}
