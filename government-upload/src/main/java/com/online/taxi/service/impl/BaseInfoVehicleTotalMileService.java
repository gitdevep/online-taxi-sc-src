package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.VehicleTotalMileRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("baseInfoVehicleTotalMileService")
public class BaseInfoVehicleTotalMileService implements YiPinService {

    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        VehicleTotalMileRequest vehicleTotalMileRequest = new VehicleTotalMileRequest(IPCUrl.BASEINFOVEHICLETOTALMILE.getKey(), IPCUrl.BASEINFOVEHICLETOTALMILE.getValue());
        vehicleTotalMileRequest.setAddress((Integer) message.get("Address"));
        vehicleTotalMileRequest.setVehicleNo((String) message.get("VehicleNo"));
        vehicleTotalMileRequest.setTotalMile((Integer) message.get("TotalMile"));
        vehicleTotalMileRequest.setFlag((Integer) message.get("Flag"));
        vehicleTotalMileRequest.setUpdateTime((Long) message.get("UpdateTime"));

        return vehicleTotalMileRequest;
    }

}
