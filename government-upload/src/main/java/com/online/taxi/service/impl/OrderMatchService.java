package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.OrderMatchRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 上报订单成功信息 Created by wn on 2018/1/29.
 */
@Service("orderMatchService")
public class OrderMatchService implements YiPinService {

    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        OrderMatchRequest orderMatchRequest = new OrderMatchRequest(IPCUrl.ORDERMATCH.getKey(), IPCUrl.ORDERMATCH.getValue());
        orderMatchRequest.setAddress((Integer) message.get("Address"));
        orderMatchRequest.setOrderId((String) message.get("OrderId"));
        orderMatchRequest.setEncrypt((Integer) message.get("Encrypt"));
        orderMatchRequest.setLicenseId((String) message.get("LicenseId"));
        orderMatchRequest.setDriverPhone((String) message.get("DriverPhone"));
        orderMatchRequest.setVehicleNo((String) message.get("VehicleNo"));
        orderMatchRequest.setDistributeTime((Long) message.get("DistributeTime"));
        
        return orderMatchRequest;
    }

}
