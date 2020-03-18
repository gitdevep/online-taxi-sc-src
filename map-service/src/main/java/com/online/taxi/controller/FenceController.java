package com.online.taxi.controller;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.map.request.FenceInRequest;
import com.online.taxi.dto.map.request.FenceRequest;
import com.online.taxi.entity.FenceCreateEntity;
import com.online.taxi.response.AmapFenceInResponse;
import com.online.taxi.response.AmapFenceSearchResponse;
import com.online.taxi.service.FenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * @date 2018/8/20
 */
@RestController
@RequestMapping("/fence")
public class FenceController {

	@Autowired
	private FenceService fenceService;
	
	@GetMapping(value="/isInFence")
	public ResponseResult isInFence(FenceInRequest fenceRequest){
		String longitude = fenceRequest.getLongitude();
		String latitude = fenceRequest.getLatitude();
		String diu = fenceRequest.getDiu();
		AmapFenceInResponse amapFenceInResponse = fenceService.isInFence(longitude, latitude, diu);
		return ResponseResult.success(amapFenceInResponse);
	}
	
	@PostMapping(value="/meta")
	public ResponseResult create(@RequestBody FenceRequest fenceRequest){
		return fenceService.createFence(fenceRequest.getGid(),fenceRequest.getName(),
				fenceRequest.getPoints(), fenceRequest.getDescription(), fenceRequest.getValidTime(),
				fenceRequest.getEnable());
	}
	
	@GetMapping(value="/search")
	public ResponseResult search(FenceRequest fenceRequest){
		
		AmapFenceSearchResponse amapFenceSearchResponse = fenceService.searchFence(fenceRequest.getId(),
				fenceRequest.getGid(),fenceRequest.getName(),fenceRequest.getPageNo()+"",
				fenceRequest.getPageSize()+"",fenceRequest.getEnable(), fenceRequest.getStartTime(),
				fenceRequest.getEndTime());
		return ResponseResult.success(amapFenceSearchResponse);
	}

	@GetMapping(value="/searchByGids")
	public ResponseResult searchByGids(FenceRequest fenceRequest){
		return fenceService.searchFence(fenceRequest.getGids());
	}

	@PostMapping(value="/changeStatus")
	public ResponseResult changeStatus(@RequestBody FenceRequest fenceRequest){
		FenceCreateEntity fence = fenceService.changeStatus(fenceRequest.getGid(),fenceRequest.getEnable());
		return ResponseResult.success(fence);
	}

	@GetMapping(value="/delete/{gid}")
	public ResponseResult changeStatus(@PathVariable String gid){
		fenceService.delFence(gid);
		return ResponseResult.success(null);
	}
}
