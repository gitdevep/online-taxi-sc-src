package com.online.taxi.controller;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.request.UserRequest;
import com.online.taxi.service.UserStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户
 * @date 2018/10/15
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserStatisticsService userStatisticsService;

    @PostMapping("/user_statistics")
    public ResponseResult userStatistics(@RequestBody UserRequest request){
        return userStatisticsService.userStatistics(request);
    }
}
