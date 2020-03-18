package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.CompanyPermitRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 * @description 报部：网约车平台经营许可信息
 * @author jxl
 * @version
 * @date 2018年1月29日
 */
@Service
public class BaseInfoCompanyPermitService implements YiPinService {

    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        CompanyPermitRequest companyPermitRequest = new CompanyPermitRequest(IPCUrl.BASEINFOCOMPANYPERMIT.getKey(), IPCUrl.BASEINFOCOMPANYPERMIT.getValue());
        companyPermitRequest.setAddress((Integer) message.get("Address"));
        companyPermitRequest.setCertificate((String) message.get("Certificate"));
        companyPermitRequest.setOperationArea((String) message.get("OperationArea"));
        companyPermitRequest.setOwnerName((String) message.get("OwnerName"));
        companyPermitRequest.setOrganization((String) message.get("Organization"));   
        companyPermitRequest.setStartDate((Long) message.get("StartDate"));
        companyPermitRequest.setStopDate((Long) message.get("StopDate"));
        companyPermitRequest.setCertifyDate((Long) message.get("CertifyDate"));
        
        companyPermitRequest.setState((String) message.get("State"));
        companyPermitRequest.setFlag((Integer) message.get("Flag"));
        companyPermitRequest.setUpdateTime((Long) message.get("UpdateTime"));
        return companyPermitRequest;
    }

}
