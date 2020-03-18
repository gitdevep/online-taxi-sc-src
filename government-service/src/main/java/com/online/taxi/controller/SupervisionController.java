package com.online.taxi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 监管上报总控制器
 *
 * @date 2018/8/23
 */
@RestController
public class SupervisionController {

    @GetMapping("/")
    public String index() {
        return null;
    }
}
