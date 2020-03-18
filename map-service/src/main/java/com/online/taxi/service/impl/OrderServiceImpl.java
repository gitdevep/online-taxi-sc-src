package com.online.taxi.service.impl;

import com.online.taxi.constant.AmapUrlConfig;
import com.online.taxi.dto.map.BaseResponse;
import com.online.taxi.dto.map.request.OrderRequest;
import com.online.taxi.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.UUID;
/**
 *
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	@Value("${amap.key}")
    private String amapKey;

    @Value("${amap.sid}")
    private String amapSid;

    @Autowired
	private RestTemplate restTemplate;

    @Override
    public String order(OrderRequest orderRequest) {

		Long timestamp = Calendar.getInstance().getTimeInMillis();
		String msgID = UUID.randomUUID().toString();

		JSONObject request = new JSONObject();
		request.put("msgID", msgID);
		request.put("type", "orders");
		request.put("time", timestamp);
		request.put("key", amapKey);

		JSONObject data = new JSONObject();
		JSONArray orders = new JSONArray();

		JSONObject order = JSONObject.fromObject(orderRequest);

		//车辆位置
		if(StringUtils.isNotBlank(orderRequest.getVehicleLongitude())
				&&StringUtils.isNotBlank(orderRequest.getVehicleLatitude())) {
			order.put("vehicleLocation", orderRequest.getVehicleLongitude()+","
					+orderRequest.getVehicleLatitude());
		}else {
			order.put("vehicleLocation", "");
		}

		//出发点
		if(StringUtils.isNotBlank(orderRequest.getStartLongitude())
				&& StringUtils.isNotBlank(orderRequest.getStartLatitude())) {
			order.put("start", orderRequest.getStartLongitude()+","+orderRequest.getStartLatitude());
		}else {
			order.put("start", "");
		}
		//终点
		if(StringUtils.isNotBlank(orderRequest.getEndLongitude())
				&& StringUtils.isNotBlank(orderRequest.getEndLatitude())) {
			order.put("end", orderRequest.getEndLongitude()+","+orderRequest.getEndLatitude());
		}else {
			order.put("end", "");
		}
		//用户叫车位置
		if(StringUtils.isNotBlank(orderRequest.getUserLongitude())
				&& StringUtils.isNotBlank(orderRequest.getUserLatitude())) {
			order.put("userLocation", orderRequest.getUserLongitude()+","+orderRequest.getUserLatitude());
		}else {
			order.put("userLocation", "");
		}

		//开始服务位置
		if(StringUtils.isNotBlank(orderRequest.getServiceStartLongitude())
				&& StringUtils.isNotBlank(orderRequest.getServiceStartLatitude())) {
			order.put("serviceStart", orderRequest.getServiceStartLongitude()+","
					+orderRequest.getServiceStartLatitude());
		}

		//开始计费位置
		if(StringUtils.isNotBlank(orderRequest.getChargeStartLongitude()) && StringUtils.isNotBlank(orderRequest.getChargeStartLatitude())) {
			order.put("chargeStart", orderRequest.getChargeStartLongitude()+","+orderRequest.getChargeStartLatitude());
		}

		//结束计费位置
		if(StringUtils.isNotBlank(orderRequest.getChargeEndLongitude()) && StringUtils.isNotBlank(orderRequest.getChargeEndLatitude())) {
			order.put("chargeEnd", orderRequest.getChargeEndLongitude()+","+orderRequest.getChargeEndLatitude());
		}
		order.put("statusTime",System.currentTimeMillis());

		orders.add(order);
		data.put("orders", order);
		request.put("data", data);
		String url = AmapUrlConfig.ORDER_URL + "?key="+amapKey;
		log.info("高德地图：上传订单信息同步  请求信息：url:"+url+",body:"+request);
		BaseResponse baseResponse = restTemplate.postForObject(url, request, BaseResponse.class);
		log.info("高德地图：上传订单信息同步  返回信息：:"+JSONObject.fromObject(baseResponse).toString());

		if(null != baseResponse) {
			return baseResponse.getErrmsg();
		}else {
			return "";
		}
    }
}
