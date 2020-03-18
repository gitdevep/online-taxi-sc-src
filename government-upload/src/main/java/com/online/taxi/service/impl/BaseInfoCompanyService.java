package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.CompanyRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 网约车平台公司基本信息
 *
 * @description
 * @author jxl
 * @version
 * @date 2018年1月29日
 */
@Service("baseInfoCompanyService")
public class BaseInfoCompanyService implements YiPinService {


	@Override
	public BaseMPRequest execute(Map<String, Object> message) throws Exception {

		// 构建公司基本信息请求报文
		CompanyRequest companyRequest = new CompanyRequest(IPCUrl.BASEINFOCOMPANY.getKey(),
				IPCUrl.BASEINFOCOMPANY.getValue());
		companyRequest.setCompanyName((String) message.get("CompanyName"));
		companyRequest.setIdentifier((String) message.get("Identifier"));
		companyRequest.setAddress((Integer) message.get("Address"));
		companyRequest.setBusinessScope((String) message.get("BusinessScope"));

		companyRequest.setContactAddress((String) message.get("ContactAddress"));
		companyRequest.setEconomicType((String) message.get("EconomicType"));
		companyRequest.setRegCapital((String) message.get("RegCapital"));
		companyRequest.setLegalName((String) message.get("LegalName"));
		companyRequest.setLegalId((String) message.get("LegalID"));
		companyRequest.setLegalPhone((String) message.get("LegalPhone"));
		companyRequest.setState((Integer) message.get("State"));
		companyRequest.setFlag((Integer) message.get("Flag"));

		companyRequest.setUpdateTime((Long) message.get("UpdateTime"));

		return companyRequest;
	}

}
