package com.online.taxi.service;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.map.Route;
import com.online.taxi.dto.map.request.DistanceRequest;

/**
 * 距离测量
 */
public interface DistanceService {
	/**
	 * 测量距离
	 * @param distanceRequest
	 * @return
	 */
	ResponseResult<Route> distance(DistanceRequest distanceRequest);
	
}
