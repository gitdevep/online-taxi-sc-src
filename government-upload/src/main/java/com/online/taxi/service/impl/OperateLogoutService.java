package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.OperateLogoutRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 上报车辆经营下线信息 Created by wn on 2018/1/29.
 */
@Service("operateLogoutService")
public class OperateLogoutService implements YiPinService {

    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        OperateLogoutRequest operateLogoutRequest = new OperateLogoutRequest(IPCUrl.OPERATELOGOUT.getKey(), IPCUrl.OPERATELOGOUT.getValue());
        //Driver driver = driverService.selectById(Integer.valueOf(message.getIndexValue()));
        operateLogoutRequest.setLicenseId((String) message.get("LicenseId"));
    	operateLogoutRequest.setVehicleNo((String) message.get("VehicleNo"));
    	operateLogoutRequest.setLogoutTime((Long) message.get("LogoutTime"));
    	operateLogoutRequest.setEncrypt((Integer) message.get("Encrypt"));
    	
        return operateLogoutRequest;
    }

}
