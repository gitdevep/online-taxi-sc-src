package com.online.taxi.service.impl;

import com.online.taxi.constant.AmapUrlConfig;
import com.online.taxi.dto.map.BaseResponse;
import com.online.taxi.dto.map.request.VehicleRequest;
import com.online.taxi.response.AmapResponse;
import com.online.taxi.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.UUID;

/**
 */
@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService{

    @Value("${amap.key}")
    private String amapKey;

    @Autowired
	private RestTemplate restTemplate;

    @Override
    public String uploadCar(VehicleRequest vehicleRequest) {
        //返回值
    	AmapResponse amapResult = new AmapResponse();

    	Long timestamp = Calendar.getInstance().getTimeInMillis();
        String msgID = UUID.randomUUID().toString();

        JSONObject request = new JSONObject();

        request.put("key", amapKey);
        request.put("type", "vehicles");
        request.put("msgId", msgID);
        request.put("time", timestamp);

        JSONObject data = new JSONObject();
        JSONArray vehicles = new JSONArray();

        JSONObject vehicleJson = JSONObject.fromObject(vehicleRequest);
        vehicleJson.put("vehicleType",2);
        vehicleJson.put("location", vehicleRequest.getLongitude()+","+vehicleRequest.getLatitude());
        vehicles.add(vehicleJson);

        data.put("vehicles", vehicles);
        request.put("data", data);

        String url = AmapUrlConfig.VEHICLE_URL + "?key="+amapKey;
        log.info("高德地图：上传车辆轨迹 请求信息：url:"+url+",body:"+request);
        BaseResponse baseResponse = restTemplate.postForObject(url, request, BaseResponse.class);
        log.info("高德地图：上传车辆轨迹 返回信息："+JSONObject.fromObject(baseResponse).toString());

        if(null != baseResponse) {
			return baseResponse.getErrmsg();
		}else {
			return "";
		}
    }
}
