package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.RatedDriverPunishRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 上报驾驶员处罚信息 Created by wn on 2018/1/30.
 */
@Service("ratedDriverPunishService")
public class RatedDriverPunishService implements YiPinService {

    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        RatedDriverPunishRequest ratedDriverPunishRequest = new RatedDriverPunishRequest(IPCUrl.RATEDDRIVERPUNISH.getKey(), IPCUrl.RATEDDRIVERPUNISH.getValue());
        ratedDriverPunishRequest.setLicenseId((String) message.get("LicenseId"));
        ratedDriverPunishRequest.setPunishTime((Long) message.get("PunishTime"));
        ratedDriverPunishRequest.setPunishResult((String) message.get("PunishResult"));
        
        return ratedDriverPunishRequest;
    }

}
