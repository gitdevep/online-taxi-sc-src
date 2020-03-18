package com.online.taxi.service;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.request.GetTokenRequest;

/**
 * @date 2018/9/5
 **/
public interface DriverRegistHandleService {

    ResponseResult checkIn(GetTokenRequest getTokenRequest);

}
