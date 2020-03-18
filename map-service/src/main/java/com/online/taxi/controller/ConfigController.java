package com.online.taxi.controller;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2018/10/26
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @GetMapping("/sid")
    public ResponseResult getConfig(){

        return configService.getSid();
    }
}
