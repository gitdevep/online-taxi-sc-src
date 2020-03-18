package com.online.taxi.controller;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.request.CarRequest;
import com.online.taxi.service.CarAmountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 车辆统计
 * @date 2018/10/15
 */
@RestController
@RequestMapping("/car")
@Slf4j
public class CarController {

    @Autowired
    private CarAmountService carAmountService;

    /**
     * 车辆统计接口
     * @param carRequest
     * @return
     */
    @PostMapping("/car_amount")
    public ResponseResult carAmount(@RequestBody CarRequest carRequest){
        return carAmountService.carAmount(carRequest);
    }
}
