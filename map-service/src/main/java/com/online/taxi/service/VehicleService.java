package com.online.taxi.service;

import com.online.taxi.dto.map.request.VehicleRequest;

/**
 * 
 */
public interface VehicleService {

	/**
	 * 同步车辆
	 * @param vehicleRequest
	 * @return
	 */
	public String uploadCar(VehicleRequest vehicleRequest);
}
