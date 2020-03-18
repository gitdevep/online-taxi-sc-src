package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.CompanyPayRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 * @description 公司支付基础信息请求报文
 * @author jxl
 * @version
 * @date 2018年1月29日
 */
@Service
public class BaseInfoCompanyPayService implements YiPinService {


    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {

        CompanyPayRequest companyPayRequest = new CompanyPayRequest(IPCUrl.BASEINFOCOMPANYPAY.getKey(), IPCUrl.BASEINFOCOMPANYPAY.getValue());
        
        companyPayRequest.setPayName((String) message.get("PayName"));
        companyPayRequest.setPayId((String) message.get("PayId"));
        companyPayRequest.setPayType((String) message.get("PayType"));
        companyPayRequest.setPayScope((String) message.get("PayScope"));
        companyPayRequest.setPrepareBank((String) message.get("PrepareBank"));
        companyPayRequest.setCountDate((Integer) message.get("CountDate"));
        companyPayRequest.setState((Integer) message.get("State"));
        companyPayRequest.setFlag((Integer) message.get("Flag"));
        companyPayRequest.setUpdateTime((Long) message.get("UpdateTime"));
        
        return companyPayRequest;
    }



}
