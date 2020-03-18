package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.OperateArriveRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 上报经营到达信息
 * Created by wn on 2018/1/29.
 */
@Service("operateArriveService")
public class OperateArriveService implements YiPinService {


    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        OperateArriveRequest operateArriveRequest = new OperateArriveRequest(IPCUrl.OPERATEARRIVE.getKey(), IPCUrl.OPERATEARRIVE.getValue());
        operateArriveRequest.setOrderId((String) message.get("OrderId"));
        operateArriveRequest.setDestLongitude((Long) message.get("DestLongitude"));
        operateArriveRequest.setDestLatitude((Long) message.get("DestLatitude"));
        operateArriveRequest.setEncrypt((Integer) message.get("Encrypt"));
        operateArriveRequest.setDestTime((Long) message.get("DestTime"));
        operateArriveRequest.setDriveMile((Long) message.get("DriveMile"));
        operateArriveRequest.setDriveTime((Long) message.get("DriveTime"));
        
        return operateArriveRequest;
    }

}
