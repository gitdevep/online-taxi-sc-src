package com.online.taxi.service;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.map.request.DispatchRequest;

/**
 * 
 */
public interface DispatchService {
	
	/**
	 * 调度车辆
	 * @param dispatchRequest
	 * @return
	 */
	ResponseResult dispatch(DispatchRequest dispatchRequest);
}
