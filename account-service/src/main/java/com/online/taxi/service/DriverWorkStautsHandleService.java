package com.online.taxi.service;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.request.DriverWorkStatusRequest;

/**
 * @date 2018/9/5
 **/
public interface DriverWorkStautsHandleService {

    ResponseResult changeWorkStatus(DriverWorkStatusRequest driverWorkStatusRequest);
}
