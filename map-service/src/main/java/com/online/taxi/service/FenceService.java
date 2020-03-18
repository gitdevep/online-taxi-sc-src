package com.online.taxi.service;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.entity.FenceCreateEntity;
import com.online.taxi.response.AmapFenceInResponse;
import com.online.taxi.response.AmapFenceSearchResponse;

import java.util.List;

/**
 * 
 */
public interface FenceService {

	AmapFenceInResponse isInFence(String longitude,String latitude,String diu);

	ResponseResult createFence(String gid, String name, String points , String description, String validTime, String enable);

	FenceCreateEntity changeStatus(String gid ,String enable);

	AmapFenceSearchResponse searchFence(String id, String gid, String name, String pageNo, String pageSize, String enable,
										String startTime, String endTime);

	ResponseResult searchFence(List<String> gids);

	Boolean delFence(String gid);
}
