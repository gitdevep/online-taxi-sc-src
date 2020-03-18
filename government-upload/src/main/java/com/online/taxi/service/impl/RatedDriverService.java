package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.RatedDriverRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 上报驾驶员信誉信息 Created by wn on 2018/1/30.
 */
@Service("ratedDriverService")
public class RatedDriverService implements YiPinService {

    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        RatedDriverRequest ratedDriverRequest = new RatedDriverRequest(IPCUrl.RATEDDRIVER.getKey(), IPCUrl.RATEDDRIVER.getValue());
        ratedDriverRequest.setLicenseId((String) message.get("LicenseId"));
      	ratedDriverRequest.setLevel((Long) message.get("Level"));
        ratedDriverRequest.setTestDate((Long) message.get("TestDate"));
        ratedDriverRequest.setTestDepartment((String) message.get("TestDepartment"));
        
        return ratedDriverRequest;
    }

}
