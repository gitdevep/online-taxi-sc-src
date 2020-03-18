package com.online.taxi.service.impl;

import com.online.taxi.constant.AmapEnum;
import com.online.taxi.constant.AmapResultConfig;
import com.online.taxi.constant.AmapUrlConfig;
import com.online.taxi.constatnt.BusinessInterfaceStatus;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.map.Route;
import com.online.taxi.entity.AmapResult;
import com.online.taxi.dto.map.request.DistanceRequest;
import com.online.taxi.service.DistanceService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 
 */
@Service
@Slf4j
public class DistanceServiceImpl implements DistanceService {
	
	@Value("${amap.key}")
	private String amapKey;

	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public ResponseResult<Route> distance(DistanceRequest distanceRequest) {

		StringBuilder urlBuild = new StringBuilder();
		urlBuild.append(AmapUrlConfig.DISTANCE_URL);
		urlBuild.append("?key="+amapKey);
		urlBuild.append("&origins="+distanceRequest.getOriginLongitude()+","+distanceRequest.getOriginLatitude());
		urlBuild.append("&destination="+distanceRequest.getDestinationLongitude()+","
				+distanceRequest.getDestinationLatitude());
		urlBuild.append("&output=json");

		String url = urlBuild.toString();
		
		log.info("高德地图：路径规划  请求信息："+url);
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
		log.info("高德地图：路径规划  返回信息：" + responseEntity.getBody());
		AmapResult<Route> amapResult = parseResponseEntityForRoute(responseEntity);
		if(amapResult.getStatus() == AmapEnum.OK.getCode()) {
			return ResponseResult.success(amapResult.getData());
		}else {
			return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(),
					BusinessInterfaceStatus.FAIL.getValue());
		}
	}
	
	private AmapResult<Route> parseResponseEntityForRoute(ResponseEntity<String> responseEntity) {
		AmapResult<Route> response = new AmapResult<>();
		
		try {
			Route route = new Route();
			String body = responseEntity.getBody();
			JSONObject result = JSONObject.fromObject(body);
			int status = 0;
			if(result.has(AmapResultConfig.AMAP_STATUS)) {
				status = result.getInt(AmapResultConfig.AMAP_STATUS);
			}
			if(status == 1) {
				if(result.has(AmapResultConfig.AMAP_RESULTS)) {
			 		JSONArray resultArray = result.getJSONArray(AmapResultConfig.AMAP_RESULTS);
			 		for(int i=0;i<resultArray.size();i++) {
			 			JSONObject jsonObj = resultArray.getJSONObject(i);
			 			if(jsonObj.has(AmapResultConfig.AMAP_DISTANCE)) {
			 				Double distance = jsonObj.getDouble(AmapResultConfig.AMAP_DISTANCE);
			 				route.setDistance(distance);
			 			}
			 			if(jsonObj.has(AmapResultConfig.AMAP_DURATION)) {
			 				Double duration = jsonObj.getDouble(AmapResultConfig.AMAP_DURATION);
			 				route.setDuration(duration);
			 			}
			 		}
			 	}
				response.setStatus(AmapEnum.OK.getCode());
				response.setData(route);
			}else {
				response.setStatus(AmapEnum.FAIL.getCode());
			}
			
		}catch (Exception e) {
			response.setStatus(AmapEnum.EXCEPTION.getCode());
		}
		return response;
		
	}
}
