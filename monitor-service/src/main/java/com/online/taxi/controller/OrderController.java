package com.online.taxi.controller;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.request.OrderRequest;
import com.online.taxi.request.UserRequest;
import com.online.taxi.service.OrderStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单
 *
 * @date 2018/10/15
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderStatisticsService orderStatisticsService;

    @PostMapping("/order_statistics")
    public ResponseResult orderStatistics(@RequestBody OrderRequest request){
        return orderStatisticsService.orderStatistics(request);
    }
}
