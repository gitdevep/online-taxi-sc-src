package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.CompanyServiceRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 * @description 报部：公司服务机构信息
 * @author jxl
 * @version
 * @date 2018年1月29日
 */
@Service("baseInfoCompanyServiceService")
public class BaseInfoCompanyServiceService implements YiPinService {

    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        // 封装请求报文
        CompanyServiceRequest companyServiceRequest = new CompanyServiceRequest(IPCUrl.BASEINFOCOMPANYSERVICE.getKey(), IPCUrl.BASEINFOCOMPANYSERVICE.getValue());
        companyServiceRequest.setAddress((Integer) message.get("Address"));
        companyServiceRequest.setServiceName((String) message.get("ServiceName"));
        companyServiceRequest.setServiceNo((String) message.get("ServiceNo"));
        companyServiceRequest.setDetailAddress((String) message.get("DetailAddress"));
        companyServiceRequest.setResponsibleName((String) message.get("ResponsibleName"));
        companyServiceRequest.setResponsiblePhone((String) message.get("ResponsiblePhone"));
        companyServiceRequest.setManagerName((String) message.get("ManagerName"));
        companyServiceRequest.setManagerPhone((String) message.get("ManagerPhone"));
        companyServiceRequest.setMailAddress((String) message.get("MailAddress"));
        companyServiceRequest.setCreateDate((Long) message.get("CreateDate"));
        companyServiceRequest.setFlag((Integer) message.get("Flag"));
        companyServiceRequest.setState((Integer) message.get("State"));
        companyServiceRequest.setUpdateTime((Long) message.get("UpdateTime"));
        return companyServiceRequest;
    }

}
