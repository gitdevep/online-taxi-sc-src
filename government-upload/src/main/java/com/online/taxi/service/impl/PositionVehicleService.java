package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.PositionVehicleRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 上报车辆定位信息 Created by wn on 2018/1/30.
 */
@Service("positionVehicleService")
public class PositionVehicleService implements YiPinService {

    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        PositionVehicleRequest positionVehicleRequest = new PositionVehicleRequest(IPCUrl.POSITIONVEHICLE.getKey(), IPCUrl.POSITIONVEHICLE.getValue());
      positionVehicleRequest.setVehicleNo((String) message.get("VehicleNo"));
      positionVehicleRequest.setVehicleRegionCode((Integer) message.get("VehicleRegionCode"));
      positionVehicleRequest.setPositionTime((Long) message.get("PositionTime"));
      positionVehicleRequest.setLongitude((Long) message.get("Longitude"));
      positionVehicleRequest.setLatitude((Long) message.get("Latitude"));
      //positionVehicleRequest.setEncrypt((Integer) message.get("Encrypt"));
      positionVehicleRequest.setOrderId((String) message.get("OrderId"));
        
      return positionVehicleRequest;
    }

}
