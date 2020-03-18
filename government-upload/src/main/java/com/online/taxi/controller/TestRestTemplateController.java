package com.online.taxi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @date 2018/10/22
 * baseinfo/company?company=3301YPZCX78Q
 */
@RequestMapping("/baseinfo")
@RestController
public class TestRestTemplateController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/company")
    public String com(byte[] bytes) {
        System.out.println(bytes);
        return "ok";
    }

}