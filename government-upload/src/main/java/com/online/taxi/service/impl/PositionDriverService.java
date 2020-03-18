package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.PositionDriverRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 上报驾驶员定位信息 Created by wn on 2018/1/29.
 */
@Service("positionDriverService")
public class PositionDriverService implements YiPinService {

    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        PositionDriverRequest positionDriverRequest = new PositionDriverRequest(IPCUrl.POSITIONDRIVER.getKey(), IPCUrl.POSITIONDRIVER.getValue());
        positionDriverRequest.setLicenseId((String) message.get("LicenseId"));
        positionDriverRequest.setDriverRegionCode((Integer) message.get("DriverRegionCode"));
        positionDriverRequest.setVehicleNo((String) message.get("VehicleNo"));
        positionDriverRequest.setPositionTime((Long) message.get("PositionTime"));
        positionDriverRequest.setLongitude((Long) message.get("Longitude"));
        positionDriverRequest.setLatitude((Long) message.get("Latitude"));
        positionDriverRequest.setOrderId((String) message.get("OrderId"));
        
        return positionDriverRequest;
    }

}
