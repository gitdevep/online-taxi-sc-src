package com.online.taxi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.taxi.service.DriverService;

/**
 * 司机控制器
 *
 * @date 2018/8/28
 */
@RestController
@RequestMapping("driver")
public class DriverController {
    @Autowired
    private DriverService driverService;



}
