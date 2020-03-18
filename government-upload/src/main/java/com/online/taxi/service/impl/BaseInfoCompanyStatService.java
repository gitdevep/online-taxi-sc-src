package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.CompanyStatRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 公司营运规模信息Service
 * 
 * @description
 * @author jxl
 * @version
 * @date 2018年1月29日
 */
@Service
public class BaseInfoCompanyStatService implements YiPinService {


    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        CompanyStatRequest companyStatRequest = new CompanyStatRequest(IPCUrl.BASEINFOCOMPANYSTAT.getKey(), IPCUrl.BASEINFOCOMPANYSTAT.getValue());
        companyStatRequest.setVehicleNum((Integer) message.get("VehicleNum"));
        companyStatRequest.setDriverNum((Integer) message.get("DriverNum"));
        companyStatRequest.setFlag((Integer) message.get("Flag"));
        companyStatRequest.setUpdateTime((Long) message.get("UpdateTime"));
        
        return companyStatRequest;
    }

}
