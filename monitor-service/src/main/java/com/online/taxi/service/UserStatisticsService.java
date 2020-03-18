package com.online.taxi.service;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.request.UserRequest;

/**
 * 用户统计查询
 *
 * @date 2018/10/15
 */
public interface UserStatisticsService {

    /**
     * 用户统计
     * @param request
     * @return
     */
    ResponseResult userStatistics(UserRequest request);
}
