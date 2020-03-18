package com.online.taxi.service.impl;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.mapper.OrderStatisticsMapper;
import com.online.taxi.request.OrderRequest;
import com.online.taxi.response.OrderStatisticsResponse;
import com.online.taxi.response.UserResponse;
import com.online.taxi.service.OrderStatisticsService;
import com.online.taxi.util.GetBetweenDates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单统计
 * @date 2018/10/16
 */
@Service
@Slf4j
public class OrderStatisticsServiceImpl implements OrderStatisticsService {

    /**
     * 统计
     */
    private String STATISTICS ="1";

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    /**
     * 订单统计
     * @param request
     * @return
     */
    @Override
    public ResponseResult orderStatistics(OrderRequest request) {
        List<OrderStatisticsResponse> orderStatisticsResponse;
        Map<String, Object> param = new HashMap<>(6);
        param.put("period",request.getPeriod());
        param.put("check",request.getCheck());
        param.put("begin",request.getBegin());
        param.put("end",request.getEnd());
        List<String> list = new ArrayList<>();
        if(request.getPeriod().equals("1")){
            list =GetBetweenDates.getBetweenDate(request.getBegin(),request.getEnd());
        }else{
            list =GetBetweenDates.getMonthBetweenDate(request.getBegin(),request.getEnd());
        }
        param.put("list",list);
        if(STATISTICS.equals(request.getCheck())){
            orderStatisticsResponse = orderStatisticsMapper.orderStatistics(param);
        }else{
            orderStatisticsResponse = orderStatisticsMapper.selectOrderFlow(param);
        }
        return ResponseResult.success(orderStatisticsResponse);
    }
}
