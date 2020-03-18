package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseInfoCompanyFareRequest;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 网约车平台公司运价信息上报 Created by zsw on 2018/1/29.
 */
@Service("baseInfoCompanyFareService")
public class BaseInfoCompanyFareService implements YiPinService {


    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        BaseInfoCompanyFareRequest baseInfoCompanyFareRequest = new BaseInfoCompanyFareRequest(IPCUrl.BASEINFOCOMPANYFARE.getKey(), IPCUrl.BASEINFOCOMPANYFARE.getValue());

        baseInfoCompanyFareRequest.setAddress((Integer) message.get("Address"));
        baseInfoCompanyFareRequest.setFareType((String) message.get("FareType"));
        baseInfoCompanyFareRequest.setFareValidOn((Long) message.get("FareValidOn"));
        baseInfoCompanyFareRequest.setStartFare((Integer) message.get("StartFare"));
        baseInfoCompanyFareRequest.setStartMile((Integer) message.get("StartMile"));
        baseInfoCompanyFareRequest.setUnitPricePerMinute((Double) message.get("UnitPricePerMinute"));
        baseInfoCompanyFareRequest.setUnitPricePerMile((Double) message.get("UnitPricePerMile"));
        baseInfoCompanyFareRequest.setMorningPeakTimeOn((String) message.get("MorningPeakTimeOn"));
        baseInfoCompanyFareRequest.setMorningPeakTimeOff((String) message.get("MorningPeakTimeOff"));
        baseInfoCompanyFareRequest.setEveningPeakTimeOn((String) message.get("EveningPeakTimeOn"));
        baseInfoCompanyFareRequest.setEveningPeakTimeOff((String) message.get("EveningPeakTimeOff"));
        baseInfoCompanyFareRequest.setPeakUnitPrice((Integer) message.get("PeakUnitPrice"));
        baseInfoCompanyFareRequest.setPeakPriceStartMile((Integer) message.get("PeakPriceStartMile"));  
        baseInfoCompanyFareRequest.setState((Integer) message.get("State"));
        baseInfoCompanyFareRequest.setFlag((Integer) message.get("Flag"));
        baseInfoCompanyFareRequest.setUpdateTime((Long) message.get("UpdateTime"));
        return baseInfoCompanyFareRequest;
    }



}
