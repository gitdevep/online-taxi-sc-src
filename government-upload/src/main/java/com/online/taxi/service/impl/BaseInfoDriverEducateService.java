package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.DriverEducateRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description 上报网约车驾驶员培训信息
 * @author jxl
 * @version
 * @date 2018年1月30日
 */
@Service
public class BaseInfoDriverEducateService implements YiPinService {

    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        DriverEducateRequest driverEducateRequest = new DriverEducateRequest(IPCUrl.BASEINFODRIVEREDUCATE.getKey(), IPCUrl.BASEINFODRIVEREDUCATE.getValue());
        driverEducateRequest.setAddress((Integer) message.get("Address"));
        driverEducateRequest.setLicenseId((String) message.get("LicenseId"));
        driverEducateRequest.setCourseName((String) message.get("CourseName"));
        driverEducateRequest.setCourseDate((Long) message.get("CourseDate"));
        driverEducateRequest.setStartTime((String) message.get("StartTime"));
        driverEducateRequest.setStopTime((String) message.get("StopTime"));
        driverEducateRequest.setDuration((Integer) message.get("Duration"));
        driverEducateRequest.setFlag((Integer) message.get("Flag"));
        driverEducateRequest.setUpdateTime((Long) message.get("UpdateTime"));

        return driverEducateRequest;
    }

}
