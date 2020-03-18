package com.online.taxi.controller;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.map.request.DistanceRequest;
import com.online.taxi.service.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2018/8/20
 */
@RestController
public class DistanceController {

    @Autowired
    private DistanceService distanceService;

    @GetMapping(value = "/distance")
    public ResponseResult distance(DistanceRequest distanceRequest) {
        return distanceService.distance(distanceRequest);
    }
}
