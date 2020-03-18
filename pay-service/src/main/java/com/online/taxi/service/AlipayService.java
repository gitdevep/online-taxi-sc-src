package com.online.taxi.service;

import com.online.taxi.dto.ResponseResult;

import java.util.Map;

/**
 */
public interface AlipayService {

    String prePay(Integer yid , Double capital, Double giveFee, String source,Integer rechargeType,Integer orderId);

    boolean callback(Map<String, String> params);

    Boolean checkAlipaySign(Map<String, String> params);

    ResponseResult payResult(Integer orderId, String tradeNo);

}
