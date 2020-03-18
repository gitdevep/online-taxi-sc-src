package com.online.taxi.controller;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.SmsSendRequest;
import com.online.taxi.dto.sms.SmsRequest;
import com.online.taxi.service.HxSmsService;
import com.online.taxi.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 */
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private HxSmsService hxSmsService;

    @RequestMapping(value = "/send",method = RequestMethod.POST)
    public ResponseResult send(@RequestBody SmsSendRequest smsSendRequest){
        smsService.sendSms(smsSendRequest);
        return  ResponseResult.success("");
    }

    @PostMapping("/hx_send")
    public ResponseResult hxSend(@RequestBody SmsRequest smsRequest){
        return  hxSmsService.sendSms(smsRequest);
    }
}
