package com.online.taxi.service.impl;

import com.online.taxi.constant.PayConst;
import com.online.taxi.service.CommonPayService;
import org.springframework.stereotype.Service;

/**
 * @date 2018/10/15
 */
@Service
public class CommonPayServiceImpl implements CommonPayService {

	@Override
	public String createDescription(Double capital, Double giveFee,String prefix) {
		String description = "";

		if (capital.compareTo(PayConst.ZERO)>0 && giveFee.compareTo(PayConst.ZERO)>0){
			description = prefix+"（本金+赠额）";
		}else if (capital.compareTo(PayConst.ZERO) > 0){
			description = prefix+"（本金）";
		}else if(giveFee.compareTo(PayConst.ZERO)>0){
			description = prefix+"（赠额）";
		}
		return description;
	}
}
