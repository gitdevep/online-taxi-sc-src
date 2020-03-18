package com.online.taxi.service.impl;

import com.online.taxi.constant.AmapEnum;
import com.online.taxi.constant.AmapResultConfig;
import com.online.taxi.constant.AmapUrlConfig;
import com.online.taxi.dto.map.Distance;
import com.online.taxi.dto.map.Location;
import com.online.taxi.dto.map.Points;
import com.online.taxi.entity.AmapResult;
import com.online.taxi.service.RouteService;
import com.online.taxi.util.AmapLocationUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 */
@Slf4j
@Service
public class RouteServiceImpl implements RouteService {

    @Value("${amap.key}")
    private String amapKey;

    @Value("${amap.sid}")
    private String amapSid;

    @Autowired
	private RestTemplate restTemplate;

    /**
     * 查询指定车辆，在某个时间段内的里程
     *
     * @param carId
     * @param city
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Distance getRoute(String carId, String city, Long startTime, Long endTime) {

        StringBuilder urlBuild = new StringBuilder();
        urlBuild.append(AmapUrlConfig.HISTORY_ROUTE_URL);
        urlBuild.append("?key="+amapKey);
        urlBuild.append("&sid="+amapSid);

        urlBuild.append("&terminal="+carId);
        urlBuild.append("&starttime="+startTime);
        urlBuild.append("&endtime="+endTime);
        urlBuild.append("&correction=driving");

        String url = urlBuild.toString();
        log.info("高德地图：查询历史轨迹里程 请求信息：" + urlBuild.toString());
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        log.info("高德地图：查询历史轨迹里程 返回信息" + JSONObject.fromObject(responseEntity).toString());
        AmapResult<Distance> amapResult = parseRoute(responseEntity);

		if(amapResult.getStatus() == AmapEnum.OK.getCode()) {
			return amapResult.getData();
		}else {
			return null;
		}

    }

    private AmapResult<Distance> parseRoute(ResponseEntity<String> responseEntity) {
    	AmapResult<Distance> response = new AmapResult<>();
    	response.setStatus(AmapEnum.FAIL.getCode());
    	try {
    		JSONObject result = JSONObject.fromObject(responseEntity.getBody());

    		if(result.has(AmapResultConfig.AMAP_ERRORCODE)) {
                Integer status = result.getInt(AmapResultConfig.AMAP_ERRORCODE);
                if(status == AmapResultConfig.AMAP_SUCCESS_CODE) {

                    if(result.has(AmapResultConfig.AMAP_DATA)) {

                        JSONObject data = result.getJSONObject(AmapResultConfig.AMAP_DATA);
                        if(data.has(AmapResultConfig.AMAP_DISTANCE)) {
                            Double distanceSource = data.getDouble(AmapResultConfig.AMAP_DISTANCE);
                            response.setStatus(AmapEnum.OK.getCode());
                            Distance distance = new Distance();
                            distance.setDistance(distanceSource);
                            response.setData(distance);
                        }
                    }
                }
            }
    	}catch (Exception e) {
			response.setStatus(AmapEnum.EXCEPTION.getCode());
		}
    	return response;

    }

    @Override
    public Points getPointsAllPage(String carId, String city, Long startTime, Long endTime, String correction) {
		Integer pagesize = 900;
		Points response = new Points();

	    Points privateResponse = getPoints(carId, city, startTime, endTime, 1, pagesize, correction);

		Integer pointCount = privateResponse.getPointCount();
		if(pointCount == null) {
			return response;
		}

		response.setStartPoint(privateResponse.getStartPoint());
		response.setEndPoint(privateResponse.getEndPoint());

		List<Location> pointsTemp = privateResponse.getPoints();

		Integer pageMax = (pointCount / pagesize) + 1;
		for(int i=2;i<=pageMax;i++) {
			privateResponse = getPoints(carId, city, startTime, endTime, i, pagesize, correction);
			if(privateResponse != null) {
				pointsTemp.addAll(privateResponse.getPoints());
			}else {
				break;
			}

		}
		response.setPoints(pointsTemp);
		return response;

	}

	private Points getPoints(String carId, String city, Long startTime, Long endTime, Integer page, Integer pagesize, String correction) {
		Points response = new Points();
		StringBuilder urlBuild = new StringBuilder();
		urlBuild.append(AmapUrlConfig.HISTORY_POINT_URL);
		urlBuild.append("?key="+amapKey).append("&sid="+amapSid);
		urlBuild.append("&terminal="+carId);
		urlBuild.append("&starttime="+startTime);
		urlBuild.append("&endtime="+endTime);
		// urlBuild.append("&correction=driving");
		urlBuild.append("&correction=" + correction);
		urlBuild.append("&recoup=1");
		urlBuild.append("&pagesize="+pagesize);
		urlBuild.append("&page="+page);
		String url = urlBuild.toString();
		log.info("高德地图：查询历史轨迹点 请求信息：host:" + url);
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
		log.info("高德地图：查询历史轨迹点 返回信息：" + JSONObject.fromObject(responseEntity).toString());
		AmapResult<Points> amapResult = parsePoints(responseEntity);
		if(amapResult.getStatus() == AmapEnum.OK.getCode()) {
			return amapResult.getData();
		}else {
			return null;
		}

	}

    private AmapResult<Points> parsePoints(ResponseEntity<String> responseEntity) {
    	AmapResult<Points> amapResult = new AmapResult<>();
    	amapResult.setStatus(AmapEnum.FAIL.getCode());
    	try {
    		Points points = new Points();
        	JSONObject result = JSONObject.fromObject(responseEntity.getBody());

    		if(result.has(AmapResultConfig.AMAP_ERRORCODE)) {
    			Integer status = result.getInt(AmapResultConfig.AMAP_ERRORCODE);
    			if(status == AmapResultConfig.AMAP_SUCCESS_CODE) {
    				if(result.has(AmapResultConfig.AMAP_DATA)) {
    					amapResult.setStatus(AmapEnum.OK.getCode());
    					JSONObject data = result.getJSONObject(AmapResultConfig.AMAP_DATA);
    					if(data.has(AmapResultConfig.AMAP_COUNT)) {
    						points.setPointCount(data.getInt(AmapResultConfig.AMAP_COUNT));
    					}
    					if(data.has(AmapResultConfig.AMAP_START_POINT)) {
    						JSONObject startPoint = data.getJSONObject(AmapResultConfig.AMAP_START_POINT);
    						Location startLocation = new Location();
    						if(startPoint.has(AmapResultConfig.AMAP_LOCATION)) {
    							startLocation.setLongitude(AmapLocationUtils.getLongitude(startPoint.getString(AmapResultConfig.AMAP_LOCATION)));
    							startLocation.setLatitude(AmapLocationUtils.getLatitude(startPoint.getString(AmapResultConfig.AMAP_LOCATION)));
    						}
    						if(startPoint.has(AmapResultConfig.AMAP_LOCATE_TIME)) {
    							Long locatetime = startPoint.getLong(AmapResultConfig.AMAP_LOCATE_TIME);
    							startLocation.setLocateTime(locatetime);
    						}
    						points.setStartPoint(startLocation);
    					}
    					if(data.has(AmapResultConfig.AMAP_END_POINT)) {
    						JSONObject startPoint = data.getJSONObject(AmapResultConfig.AMAP_END_POINT);
    						Location endLocation = new Location();
    						if(startPoint.has(AmapResultConfig.AMAP_LOCATION)) {
    							String location = startPoint.getString(AmapResultConfig.AMAP_LOCATION);
    							endLocation.setLongitude(AmapLocationUtils.getLongitude(location));
    							endLocation.setLatitude(AmapLocationUtils.getLatitude(location));
    						}
    						if(startPoint.has(AmapResultConfig.AMAP_LOCATE_TIME)) {
    							Long locatetime = startPoint.getLong(AmapResultConfig.AMAP_LOCATE_TIME);
    							endLocation.setLocateTime(locatetime);
    						}
    						points.setEndPoint(endLocation);
    					}
    					if(data.has(AmapResultConfig.AMAP_POINTS)) {
    						JSONArray pointsSource = data.getJSONArray(AmapResultConfig.AMAP_POINTS);
    						List<Location> pointsArray = new ArrayList<Location>();
    						for(int i=0;i<pointsSource.size();i++) {
    							JSONObject point = pointsSource.getJSONObject(i);
    							Location pointBean = new Location();
    							if(point.has(AmapResultConfig.AMAP_LOCATION)) {
    								String location = point.getString(AmapResultConfig.AMAP_LOCATION);
    								pointBean.setLongitude(AmapLocationUtils.getLongitude(location));
    								pointBean.setLatitude(AmapLocationUtils.getLatitude(location));
    							}
    							if(point.has(AmapResultConfig.AMAP_LOCATE_TIME)) {
    								Long locatetime = point.getLong(AmapResultConfig.AMAP_LOCATE_TIME);
    								pointBean.setLocateTime(locatetime);
    							}
    							pointsArray.add(pointBean);
    						}
    						points.setPoints(pointsArray);
    					}
    					amapResult.setData(points);
    				}
    			}
    		}
    		return amapResult;
    	}catch (Exception e) {
			return null;
		}

    }
}
