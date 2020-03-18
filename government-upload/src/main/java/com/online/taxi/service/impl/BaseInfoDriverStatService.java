package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.DriverStatRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description 上报驾驶员统计信息
 * @author jxl
 * @version
 * @date 2018年1月30日
 */
@Service
public class BaseInfoDriverStatService implements YiPinService {


    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        DriverStatRequest driverStatRequest = new DriverStatRequest(IPCUrl.BASEINFODRIVERSTAT.getKey(), IPCUrl.BASEINFODRIVERSTAT.getValue());
        driverStatRequest.setAddress((Integer) message.get("Address"));
        driverStatRequest.setLicenseId((String) message.get("LicenseId"));
        driverStatRequest.setCycle((Integer) message.get("Cycle"));
        driverStatRequest.setOrderCount((Integer) message.get("OrderCount"));
        driverStatRequest.setTrafficViolationCount((Integer) message.get("TrafficViolationCount"));
        driverStatRequest.setComplainedCount((Integer) message.get("ComplainedCount"));
        driverStatRequest.setFlag((Integer) message.get("Flag"));
        driverStatRequest.setUpdateTime((Long) message.get("UpdateTime"));

        return driverStatRequest;
    }

}
