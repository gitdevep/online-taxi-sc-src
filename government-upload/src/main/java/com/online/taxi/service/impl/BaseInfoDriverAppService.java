package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.DriverAppRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 * @description 上报驾驶员移动终端信息
 * @author jxl
 * @version
 * @date 2018年1月30日
 */
@Service
public class BaseInfoDriverAppService implements YiPinService {

    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {

        DriverAppRequest driverAppRequest = new DriverAppRequest(IPCUrl.BASEINFODRIVERAPP.getKey(), IPCUrl.BASEINFODRIVERAPP.getValue());
        driverAppRequest.setAddress((Integer) message.get("Address"));
        driverAppRequest.setLicenseId((String) message.get("LicenseId"));
        driverAppRequest.setDriverPhone((String) message.get("DriverPhone"));
        driverAppRequest.setNetType((Integer) message.get("NetType"));
        driverAppRequest.setAppVersion((String) message.get("AppVersion"));
        driverAppRequest.setMapType((Integer) message.get("MapType"));
        driverAppRequest.setState((Integer) message.get("State"));
        driverAppRequest.setFlag((Integer) message.get("Flag"));
        driverAppRequest.setUpdateTime((Long) message.get("UpdateTime"));
        
        return driverAppRequest;
    }

}
