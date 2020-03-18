package com.online.taxi.service;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.push.PushRequest;

/**
 */
public interface JpushService {

    ResponseResult sendSingleJpushToApp(PushRequest pushRequest,int channelType);

}
