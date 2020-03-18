package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.OrderCancelRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 上报订单撤销信息 Created by wn on 2018/1/29.
 */
@Service("orderCancelService")
public class OrderCancelService implements YiPinService {

    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        OrderCancelRequest orderCancelRequest = new OrderCancelRequest(IPCUrl.ORDERCANCEL.getKey(), IPCUrl.ORDERCANCEL.getValue());
        // 为了避免封装报文时，取不到订单表中的部分字段，休眠两秒钟再执行后续逻辑
        Thread.sleep(2000);
        orderCancelRequest.setAddress((Integer) message.get("Address"));
        orderCancelRequest.setOrderId((String) message.get("OrderId"));
        orderCancelRequest.setCancelTime((Long) message.get("CancelTime"));
        orderCancelRequest.setOperator((String) message.get("Operator"));
        orderCancelRequest.setCancelTypeCode((String) message.get("CancelTypeCode"));

        return orderCancelRequest;
    }

}
