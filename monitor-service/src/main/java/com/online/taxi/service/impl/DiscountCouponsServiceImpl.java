package com.online.taxi.service.impl;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.mapper.DiscountCouponsMapper;
import com.online.taxi.request.DiscountCouponsRequest;
import com.online.taxi.response.DiscountCouponsResponse;
import com.online.taxi.service.DiscountCouponsService;
import com.online.taxi.util.GetBetweenDates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述
 *
 * @date 2018/10/17
 */
@Service
@Slf4j
public class DiscountCouponsServiceImpl implements DiscountCouponsService {

    @Autowired
    private DiscountCouponsMapper discountCouponsMapper;

    /**
     * 优惠券统计
     * @param request
     * @return
     */
    @Override
    public ResponseResult DiscountCouponsStatistics(DiscountCouponsRequest request) {
        List<DiscountCouponsResponse> discountCouponsResponses;
        Map<String, Object> param = new HashMap<>(3);
        param.put("period",request.getPeriod());
        param.put("begin",request.getBegin());
        param.put("end",request.getEnd());
        List<String> list = new ArrayList<>();
        if(request.getPeriod().equals("1")){
            list =GetBetweenDates.getBetweenDate(request.getBegin(),request.getEnd());
        }else{
            list =GetBetweenDates.getMonthBetweenDate(request.getBegin(),request.getEnd());
        }
        param.put("list",list);
        discountCouponsResponses = discountCouponsMapper.discountCoupons(param);
        return ResponseResult.success(discountCouponsResponses);
    }
}
