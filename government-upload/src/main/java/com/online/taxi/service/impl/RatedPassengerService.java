package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.RatedPassengerRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 上报乘客评价信息
 * Created by wn on 2018/1/27.
 */
@Service("ratedPassengerService")
public class RatedPassengerService implements YiPinService {

    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        RatedPassengerRequest ratedPassengerRequest = new RatedPassengerRequest(IPCUrl.RATEDPASSENGER.getKey(), IPCUrl.RATEDPASSENGER.getValue());
    	ratedPassengerRequest.setOrderId((String) message.get("OrderId"));
    	ratedPassengerRequest.setEvaluateTime((Long) message.get("EvaluateTime"));
        ratedPassengerRequest.setServiceScore((Long) message.get("ServiceScore"));

        return ratedPassengerRequest;
    }

}
