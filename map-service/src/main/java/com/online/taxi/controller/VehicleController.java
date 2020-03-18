package com.online.taxi.controller;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.map.request.VehicleRequest;
import com.online.taxi.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 */
@RestController
public class VehicleController {

	@Autowired
	private VehicleService vehicleService;
	
    @RequestMapping(value = "/vehicle",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult vehicle(@RequestBody  VehicleRequest vehicleRequest){
    	
    	vehicleService.uploadCar(vehicleRequest);
    	return ResponseResult.success(null);
    }
}
