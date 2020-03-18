package com.online.taxi.service;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.request.PayResultRequest;
import com.online.taxi.response.WeixinPayResponse;

/**
 * @date 2018/8/17
 */
public interface WeixinPayService {

    WeixinPayResponse prePay(Integer yid , Double capital, Double giveFee, String source, Integer rechargeType, Integer orderId);

    Boolean callback(String reqXml);

    ResponseResult payResult(PayResultRequest payResultRequest);
}
