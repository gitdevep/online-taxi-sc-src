package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.VehiclelnsuranceRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("baseInfoVehicleInsuranceService")
public class BaseInfoVehicleInsuranceService implements YiPinService {

    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        VehiclelnsuranceRequest vehiclelnsuranceRequest = new VehiclelnsuranceRequest(IPCUrl.BASEINFOVEHICLEINSURANCE.getKey(), IPCUrl.BASEINFOVEHICLEINSURANCE.getValue());
        //vehiclelnsuranceRequest.setCompanyId(vehicleInsurance.getCompanyId());
        vehiclelnsuranceRequest.setVehicleNo((String) message.get("VehicleNo"));
        vehiclelnsuranceRequest.setInsurCom((String) message.get("InsurCom"));
        vehiclelnsuranceRequest.setInsurNum((String) message.get("InsurNum"));
        vehiclelnsuranceRequest.setInsurType((String) message.get("InsurType"));
        vehiclelnsuranceRequest.setInsurCount((Integer) message.get("InsurCount"));
        vehiclelnsuranceRequest.setInsurEff((Long) message.get("InsurEff"));
        vehiclelnsuranceRequest.setInsurExp((Long) message.get("InsurExp"));
        vehiclelnsuranceRequest.setFlag((Integer) message.get("Flag"));
        vehiclelnsuranceRequest.setUpdateTime((Long) message.get("UpdateTime"));


        return vehiclelnsuranceRequest;
    }

}
