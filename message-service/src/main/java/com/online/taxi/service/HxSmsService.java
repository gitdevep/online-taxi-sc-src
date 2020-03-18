package com.online.taxi.service;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.sms.SmsRequest;

/**
 * 华信短信发送
 */
public interface HxSmsService {

    /**
     * 短信发送
     * @param request
     * @return
     */
    ResponseResult sendSms(SmsRequest request);
}
