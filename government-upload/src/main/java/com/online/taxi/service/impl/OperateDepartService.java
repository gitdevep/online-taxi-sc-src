package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.OperateDepartRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 上报经营出发信息 Created by wn on 2018/1/29.
 */
@Service("operateDepartService")
public class OperateDepartService implements YiPinService {

    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        OperateDepartRequest operateDepartRequest = new OperateDepartRequest(IPCUrl.OPERATEDEPART.getKey(), IPCUrl.OPERATEDEPART.getValue());
        
        operateDepartRequest.setOrderId((String) message.get("OrderId"));
        operateDepartRequest.setLicenseId((String) message.get("LicenseId"));
        operateDepartRequest.setFareType((String) message.get("FareType"));
        operateDepartRequest.setVehicleNo((String) message.get("VehicleNo"));
        operateDepartRequest.setDepLongitude((Long) message.get("DepLongitude"));
        operateDepartRequest.setDepLatitude((Long) message.get("DepLatitude"));
        operateDepartRequest.setEncrypt((Integer) message.get("Encrypt"));
        operateDepartRequest.setDepTime((Long) message.get("DepTime"));

        return operateDepartRequest;
    }

}
