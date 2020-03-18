package com.online.taxi.service.impl;

import com.online.taxi.constant.AmapResultConfig;
import com.online.taxi.constant.AmapUrlConfig;
import com.online.taxi.constatnt.BusinessInterfaceStatus;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.entity.FenceCreateEntity;
import com.online.taxi.entity.FenceEntity;
import com.online.taxi.response.AmapFenceInResponse;
import com.online.taxi.response.AmapFenceSearchResponse;
import com.online.taxi.service.FenceService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

/**
 *
 */
@Service
@Slf4j
public class FenceServiceImpl implements FenceService{

	@Value("${amap.key}")
	private String amapKey;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public AmapFenceInResponse isInFence(String longitude,String latitude,String diu) {

		AmapFenceInResponse response = new AmapFenceInResponse();
		Boolean isInFence = false;

		StringBuilder urlBuild = new StringBuilder();
		urlBuild.append(AmapUrlConfig.FENCE_STATUS_URL);
		urlBuild.append("?key="+amapKey);
		urlBuild.append("&diu="+diu);
		Long nowTimeSecond = Calendar.getInstance().getTimeInMillis() / 1000;
		urlBuild.append("&locations="+longitude+","+latitude+","+nowTimeSecond);

		String url = urlBuild.toString();
		log.info("高德地图：查询点是否在围栏内：url:"+url);
		String resultString = restTemplate.getForObject(url, String.class);
        log.info("高德地图：查询点是否在围栏内：结果:"+resultString);

		JSONObject result = JSONObject.fromObject(resultString);
        if(result.has(AmapResultConfig.AMAP_DATA)) {
        	JSONObject data = result.getJSONObject(AmapResultConfig.AMAP_DATA);
        	if(data.has(AmapResultConfig.AMAP_FENCING_EVENT_LIST)) {
        		JSONArray fencingEventList = data.getJSONArray(AmapResultConfig.AMAP_FENCING_EVENT_LIST);
        		for(int i=0;i<fencingEventList.size();i++) {
        			JSONObject json = fencingEventList.getJSONObject(i);
        			if(json.has(AmapResultConfig.AMAP_CLIENT_STATUS)) {
        				String clientStatus = json.getString(AmapResultConfig.AMAP_CLIENT_STATUS);
        				if(clientStatus.trim().equals(AmapResultConfig.AMAP_IN)) {
        					isInFence = true;
        				}

        			}
        		}
        	}
        }
        log.info("高德地图：查询点是否在围栏内：isInFence:"+isInFence);
//		isInFence = true;
		response.setInFence(isInFence);
		return response;

	}

	@Override
	public ResponseResult createFence(String gid , String name, String points , String description, String validTime, String enable) {

		FenceCreateEntity fence = new FenceCreateEntity();
		JSONObject request = new JSONObject();
		request.put("name", name);
		request.put("points", points);
		request.put("repeat", "Mon,Tues,Wed,Thur,Fri,Sat,Sun");
		request.put("desc", description);
		request.put("valid_time", validTime);

		StringBuilder url = new StringBuilder();
		url.append(AmapUrlConfig.FENCE_CREATE + "?key="+amapKey);
		//更新围栏
		if(StringUtils.isNotBlank(gid)){
			url.append("&gid="+gid);
			url.append("&method=" + "patch");
		}else {
			request.put("enable", enable);
		}
		log.info("高德地图：创建围栏 请求信息：url:"+url+",body:"+request.toString());
		String resultString = restTemplate.postForObject(url.toString(), request, String.class);
		log.info("高德地图：创建围栏 返回信息："+resultString);

        JSONObject result = JSONObject.fromObject(resultString);

        if(result.has(AmapResultConfig.AMAP_DATA)) {
        	JSONObject data = result.getJSONObject(AmapResultConfig.AMAP_DATA);
        	if(data.has(AmapResultConfig.AMAP_STATUS)) {
        		int status = data.getInt(AmapResultConfig.AMAP_STATUS);

        		if(status == 0) {
					if (data.has(AmapResultConfig.GID) && data.has(AmapResultConfig.ID)){
						fence.setGid(data.getString(AmapResultConfig.GID));
						fence.setId(data.getString(AmapResultConfig.ID));
					}

					return ResponseResult.success(fence);

        		}else {
        			String message = data.getString("message");
        			return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(),message);
				}
        	}
        }

		return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(),BusinessInterfaceStatus.FAIL.getValue());
	}

	@Override
	public FenceCreateEntity changeStatus(String gid ,String enable) {

		FenceCreateEntity fence = new FenceCreateEntity();
		JSONObject request = new JSONObject();
		StringBuilder url = new StringBuilder();
		url.append(AmapUrlConfig.FENCE_CREATE + "?key="+amapKey);
		url.append("&gid="+gid);
		url.append("&method=" + "patch");
		request.put("enable", enable);

		log.info("高德地图：创建围栏 请求信息：url:"+url+",body:"+request.toString());
		String resultString = restTemplate.postForObject(url.toString(), request, String.class);
		JSONObject result = JSONObject.fromObject(resultString);
		log.info("高德地图：创建围栏 返回信息："+result.toString());
		if(result.has(AmapResultConfig.AMAP_DATA)) {
			JSONObject data = result.getJSONObject(AmapResultConfig.AMAP_DATA);
			if(data.has(AmapResultConfig.AMAP_STATUS)) {
				int status = data.getInt(AmapResultConfig.AMAP_STATUS);
				if(status == 0) {
					fence.setGid(gid);
				}
			}
		}

		return fence;
	}

	@Override
	public AmapFenceSearchResponse searchFence(String id, String gid, String name,
			String pageNo, String pageSize, String enable, String startTime, String endTime) {
		AmapFenceSearchResponse fenceResponseData = new AmapFenceSearchResponse();
		List<FenceEntity> fenceList = new ArrayList<>();

		StringBuilder urlBuild = new StringBuilder();
		urlBuild.append(AmapUrlConfig.FENCE_CREATE).append("?key=").append(amapKey);
		if (StringUtils.isNotBlank(id)) {
			urlBuild.append("&id=").append(id);
		}
		if (StringUtils.isNotBlank(gid)) {
			urlBuild.append("&gid=").append(gid);
		}
		if(StringUtils.isNotBlank(name)) {
			urlBuild.append("&name=" + name);
		}
		if (StringUtils.isNotBlank(pageNo)) {
			urlBuild.append("&pageNo=" + pageNo);
		}
		if (StringUtils.isNotBlank(pageSize)) {
			urlBuild.append("&pageSize=" + pageSize);
		}
		if (StringUtils.isNotBlank(enable)) {
			urlBuild.append("&enable=" + enable);
		}
		if (StringUtils.isNotBlank(startTime)) {
			urlBuild.append("&startTime="+startTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			urlBuild.append("&endTime=" + endTime);
		}

		String url = urlBuild.toString();
		log.info("高德地图：查询围栏 请求信息：url:"+url);
		String resultString = restTemplate.getForObject(url, String.class);
		log.info("高德地图：查询围栏 请求信息:"+resultString);
		JSONObject result = JSONObject.fromObject(resultString);

        if(result.has(AmapResultConfig.AMAP_DATA)) {
        	JSONObject data = result.getJSONObject(AmapResultConfig.AMAP_DATA);
        	JSONArray array = data.getJSONArray("rs_list");
        	for (int i = 0; i <array.size(); i++) {
        		JSONObject o = array.getJSONObject(i);
        		FenceEntity fence = new FenceEntity();
        		fence.setAdcode(o.getString("adcode"));
        		fence.setAlertCondition(o.getString("alert_condition"));
        		fence.setCenter(o.getString("center"));
        		fence.setCreateTime(o.getString("create_time"));
        		fence.setEnable(o.getString("enable"));
        		fence.setFixedDate(o.getString("fixed_date"));
        		fence.setGid(o.getString("gid"));
        		fence.setId(o.getString("id"));
        		fence.setName(o.getString("name"));
        		fence.setPoints(o.getString("points"));
        		fence.setRadius(o.getDouble("radius"));
        		fence.setRepeat(o.getString("repeat"));
        		fence.setTime(o.getString("time"));
        		fence.setValidTime(o.getString("valid_time"));
        		fenceList.add(fence);
        	}
        	fenceResponseData.setList(fenceList);
        	fenceResponseData.setCount(data.getInt("total_record"));
        }

		return fenceResponseData;
	}

	@Override
	public ResponseResult searchFence(List<String> gids) {

		HashSet h = new HashSet(gids);
		gids.clear();
		gids.addAll(h);
		List<FenceEntity> list = new ArrayList<>();
		for (String gid:gids) {
			AmapFenceSearchResponse fenceResponseData = searchFence("",gid,"","","",
					"","","");
			if (!fenceResponseData.getList().isEmpty()){
				list.addAll(fenceResponseData.getList());
			}
		}
		return ResponseResult.success(list);
	}

	@Override
	public Boolean delFence(String gid) {
		JSONObject request = new JSONObject();
		StringBuilder urlBuild = new StringBuilder();
		urlBuild.append(AmapUrlConfig.FENCE_CREATE);
		urlBuild.append("?key="+amapKey);
		urlBuild.append("&gid=" + gid);
		urlBuild.append("&method=" + "delete");

		String url = urlBuild.toString();
		log.info("高德地图：删除围栏 请求信息：url:"+url);
		String resultString = restTemplate.postForObject(url, request, String.class);
		log.info("高德地图：删除围栏 返回信息:"+resultString);
		return true;
	}

}
