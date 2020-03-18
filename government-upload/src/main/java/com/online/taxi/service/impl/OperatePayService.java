package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.OperatePayRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 上报经营支付信息 Created by wn on 2018/1/29.
 */
@Service("operatePayService")
public class OperatePayService implements YiPinService {

    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        OperatePayRequest operatePayRequest = new OperatePayRequest(IPCUrl.OPERATEPAY.getKey(), IPCUrl.OPERATEPAY.getValue());
        operatePayRequest.setOrderId((String) message.get("OrderId"));
        operatePayRequest.setOnArea((Integer) message.get("OnArea"));
        operatePayRequest.setLicenseId((String) message.get("LicenseId"));
        operatePayRequest.setFareType((String) message.get("FareType"));
        operatePayRequest.setVehicleNo((String) message.get("VehicleNo"));
        operatePayRequest.setBookDepTime((Long) message.get("BookDepTime"));
        operatePayRequest.setDepLongitude((Long) message.get("DepLongitude"));
        operatePayRequest.setDepLatitude((Long) message.get("DepLatitude"));
        operatePayRequest.setDepTime((Long) message.get("DepTime"));
        operatePayRequest.setDestLongitude((Long) message.get("DestLongitude"));
        operatePayRequest.setDestLatitude((Long) message.get("DestLatitude"));
        operatePayRequest.setDestTime((Long) message.get("DestTime"));
        operatePayRequest.setDriveMile((Long) message.get("DriveMile"));
        operatePayRequest.setDriveTime((Long) message.get("DriveTime"));
        operatePayRequest.setFactPrice((Double) message.get("FactPrice"));
        operatePayRequest.setFarUpPrice((Double) message.get("FarUpPrice"));
        operatePayRequest.setOtherUpPrice((Double) message.get("OtherUpPrice"));
        operatePayRequest.setPayState((String) message.get("PayState"));
        operatePayRequest.setInvoiceStatus((String) message.get("InvoiceStatus"));
        
        return operatePayRequest;
    }

}
