package com.online.taxi.controller;

import com.online.taxi.test.TestProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2018/10/16
 */
@RestController
@RequestMapping("/produce")
public class TestProduceController {

    @Autowired
    private TestProduceService testProduceService;

    @GetMapping("/test/{content}")
    public String produce(@PathVariable String content){
        return  testProduceService.produce(content);
    }
}
