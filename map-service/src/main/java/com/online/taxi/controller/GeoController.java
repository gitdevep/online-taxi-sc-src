package com.online.taxi.controller;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.map.request.GeoRequest;
import com.online.taxi.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2018/8/20
 */
@RestController
@RequestMapping("geo")
public class GeoController {

    @Autowired
    private GeoService geoService;

    @GetMapping("/cityCode")
    public ResponseResult dispatch(GeoRequest geoRequest) {
        return geoService.getCityCode(geoRequest);
    }
}
