package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.OrderCreateRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 上报订单生成信息 Created by wn on 2018/1/29.
 */
@Service("orderCreateService")
public class OrderCreateService implements YiPinService {

    @Override
    public BaseMPRequest execute(Map<String, Object> message) throws Exception {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(IPCUrl.ORDERCREATE.getKey(), IPCUrl.ORDERCREATE.getValue());
        orderCreateRequest.setAddress((Integer) message.get("Address"));
        orderCreateRequest.setOrderId((String) message.get("OrderId"));
        orderCreateRequest.setDepartTime((Long) message.get("DepartTime"));
        orderCreateRequest.setOrderTime((Long) message.get("OrderTime"));
        orderCreateRequest.setDeparture((String) message.get("Departure"));
        orderCreateRequest.setDepLongitude((Long) message.get("DepLongitude"));
        orderCreateRequest.setDepLatitude((Long) message.get("DepLatitude"));
        orderCreateRequest.setDestination((String) message.get("Destination"));
        orderCreateRequest.setDestLongitude((Long) message.get("DestLongitude"));
        orderCreateRequest.setDestLatitude((Long) message.get("DestLatitude"));
        orderCreateRequest.setEncrypt((Integer) message.get("Encrypt"));
        orderCreateRequest.setFareType(message.get("FareType").toString());

        return orderCreateRequest;
    }

}
