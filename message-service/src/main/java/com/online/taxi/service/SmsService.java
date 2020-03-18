package com.online.taxi.service;

import com.online.taxi.dto.SmsSendRequest;

/**
 */
public interface SmsService {

    int sendSms(SmsSendRequest request);

}
