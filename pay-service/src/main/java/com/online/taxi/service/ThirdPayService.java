package com.online.taxi.service;

import com.online.taxi.entity.ScanPayResData;

import java.util.Map;

/**
 * @date 2018/9/14
 */
public interface ThirdPayService {

    void insertAlipay(Map<String, String> params);

    void insertWeixinpay(ScanPayResData scanPayResData);
}
