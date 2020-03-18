package com.online.taxi.service;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.request.GetTokenRequest;

/**
 * @date 2018/08/15
 **/
public interface PassengerRegistHandleService {

     ResponseResult handle(GetTokenRequest getTokenRequest) ;

     ResponseResult checkOut(GetTokenRequest request) throws Exception;
}
