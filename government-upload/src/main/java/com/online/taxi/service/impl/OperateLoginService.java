package com.online.taxi.service.impl;

import java.util.Map;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.OperateLoginRequest;
import com.online.taxi.service.YiPinService;

import org.springframework.stereotype.Service;


/**
 * 上报车辆经营上线信息 Created by wn on 2018/1/29.
 */
@Service("operateLoginService")
public class OperateLoginService implements YiPinService {

    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        OperateLoginRequest operateLoginRequest = new OperateLoginRequest(IPCUrl.OPERATELOGIN.getKey(), IPCUrl.OPERATELOGIN.getValue());
        operateLoginRequest.setLicenseId((String) message.get("LicenseId"));
    	operateLoginRequest.setVehicleNo((String) message.get("VehicleNo"));
    	operateLoginRequest.setLoginTime((Long) message.get("LoginTime"));
    	operateLoginRequest.setEncrypt((Integer) message.get("Encrypt"));

        return operateLoginRequest;
    }

}
