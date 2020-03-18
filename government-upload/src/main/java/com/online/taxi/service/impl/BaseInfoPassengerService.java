package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.PassengerInfoRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * 
 * @description 上报乘客基本信息
 * @author jxl
 * @version
 * @date 2018年1月30日
 */
@Service
public class BaseInfoPassengerService implements YiPinService {


    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        PassengerInfoRequest passengerInfoRequest = new PassengerInfoRequest(IPCUrl.BASEINFOPASSENGER.getKey(), IPCUrl.BASEINFOPASSENGER.getValue());
        
        passengerInfoRequest.setPassengerPhone((String) message.get("PassengerPhone"));
        passengerInfoRequest.setState((Integer) message.get("State"));
        passengerInfoRequest.setFlag((Integer) message.get("Flag"));
        passengerInfoRequest.setUpdateTime((Long) message.get("UpdateTime"));
        

        return passengerInfoRequest;
    }

}
