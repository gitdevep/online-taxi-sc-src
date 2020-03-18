package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.DriverRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("baseInfoDriverService")
public class BaseInfoDriverService implements YiPinService {

    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        DriverRequest driverRequest = new DriverRequest(IPCUrl.BASEINFODRIVER.getKey(), IPCUrl.BASEINFODRIVER.getValue());
        driverRequest.setAddress((Integer) message.get("Address"));
        driverRequest.setDriverPhone((String) message.get("DriverPhone"));
        driverRequest.setDriverGender((String) message.get("DriverGender"));
        driverRequest.setDriverBirthday((Long) message.get("DriverBirthday"));
        driverRequest.setDriverNation((String) message.get("DriverNation"));
        driverRequest.setDriverContactAddress((String) message.get("DriverContactAddress"));
        driverRequest.setLicenseId((String) message.get("LicenseId"));
        driverRequest.setGetDriverLicenseDate((Long) message.get("GetDriverLicenseDate"));
        driverRequest.setDriverLicenseOn((Long) message.get("DriverLicenseOn"));
        driverRequest.setDriverLicenseOff((Long) message.get("DriverLicenseOff"));
        driverRequest.setTaxiDriver((Integer) message.get("TaxiDriver"));
        driverRequest.setCertificateNo((String) message.get("CertificateNo"));
        driverRequest.setNetworkCarIssueOrganization((String) message.get("NetworkCarIssueOrganization"));
        driverRequest.setNetworkCarIssueDate((Long) message.get("NetworkCarIssueDate"));
        driverRequest.setGetNetworkCarProofDate((Long) message.get("GetNetworkCarProofDate"));
        driverRequest.setNetworkCarProofOn((Long) message.get("NetworkCarProofOn"));
        driverRequest.setNetworkCarProofOff((Long) message.get("NetworkCarProofOff"));
        driverRequest.setRegisterDate((Long) message.get("RegisterDate"));
        driverRequest.setCommercialType((Integer) message.get("CommercialType"));
        driverRequest.setContractCompany((String) message.get("ContractCompany"));
        driverRequest.setContractOn((Long) message.get("ContractOn"));
        driverRequest.setContractOff((Long) message.get("ContractOff"));
        driverRequest.setState((Integer) message.get("State"));
        driverRequest.setFlag((Integer) message.get("Flag"));
        driverRequest.setUpdateTime((Long) message.get("UpdateTime"));

        return driverRequest;
    }

}
