package com.online.taxi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.map.Dispatch;
import com.online.taxi.dto.map.Route;
import com.online.taxi.dto.map.request.DistanceRequest;
import com.online.taxi.dto.map.request.OrderRequest;
import com.online.taxi.dto.phone.BoundPhoneDto;
import com.online.taxi.dto.push.PushLoopBatchRequest;
import com.online.taxi.dto.push.PushRequest;
import com.online.taxi.dto.sms.SmsRequest;
import com.online.taxi.entity.Order;
import com.online.taxi.request.DispatchRequest;
import com.online.taxi.request.SmsSendRequest;
import com.online.taxi.request.SmsTemplateDto;
import com.online.taxi.util.DateUtils;
import com.online.taxi.util.RestTemplateHepler;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @date 2018/8/14
 */
@Service
@Slf4j
public class HttpService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ConfigService configService;

    public ResponseResult sendSms(String phone, String smsCode, Map<String, Object> templateMap) {
        String url = configService.messageServiceUrl() + "/sms/send";
        SmsSendRequest request = new SmsSendRequest();
        String[] receivers = new String[1];
        receivers[0] = phone;
        request.setReceivers(receivers);
        List<SmsTemplateDto> list = new ArrayList<>();
        SmsTemplateDto dto = new SmsTemplateDto();
        dto.setId(smsCode);
        dto.setTemplateMap(templateMap);
        list.add(dto);
        request.setData(list);

        ResponseResult response = restTemplate.postForObject(url, request, ResponseResult.class);
        return response;
    }

    public ResponseResult sendSms(String phone, String smsCode, String... content) throws Exception {
        String url = configService.messageServiceUrl() + "/sms/hx_send";
        SmsRequest request = new SmsRequest();
        request.setPhones(new String[]{phone});
        request.setTemplateId(smsCode);
        request.setContent(content);

        ResponseResult responseResult = restTemplate.postForObject(url, request, ResponseResult.class);

        return responseResult;
    }

    public ResponseResult updateAmapOrder(OrderRequest orderRequest) {
        String url = configService.mapServiceUrl() + "/order";
        ResponseResult response = restTemplate.postForObject(url, orderRequest, ResponseResult.class);
        return response;
    }

    public ResponseResult<Dispatch> dispatch(DispatchRequest dispatchRequest) {
        String url = configService.mapServiceUrl() + "/vehicleDispatch";
        ResponseResult response = restTemplate.postForObject(url, dispatchRequest, ResponseResult.class);
        try {
            log.info(response.toString());
            Dispatch o = RestTemplateHepler.parse(response, Dispatch.class);
            return ResponseResult.success(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public double calDistance(DistanceRequest distanceRequest) {
        String url = configService.mapServiceUrl() + "/distance?";
        Map<String, Object> map = new HashMap<>();
        map.put("originLongitude", distanceRequest.getOriginLongitude());
        map.put("originLatitude", distanceRequest.getOriginLatitude());
        map.put("destinationLongitude", distanceRequest.getDestinationLongitude());
        map.put("destinationLatitude", distanceRequest.getDestinationLatitude());
        String param = String.join("&", map.keySet().stream().map(k -> k + "={" + k + "}").collect(Collectors.toList()));
        url = url + param;
        double distance = Integer.MAX_VALUE;
        ResponseResult response = restTemplate.getForObject(url, ResponseResult.class, map);
        if (response.getData() != null) {
            try {
                log.info(response.toString());
                Route o = RestTemplateHepler.parse(response, Route.class);
                distance = o.getDistance();
            } catch (Exception e) {
                log.error("distance error " + e.getMessage());
            }
        }
        return distance;
    }

    public void unbind(String subId, String secretNo) {
        String url = configService.fileServiceUrl() + "phone/unbind?";
        Map<String, Object> map = new HashMap<>();
        map.put("subsId", subId);
        map.put("secretNo", secretNo);
        String param = String.join("&", map.keySet().stream().map(k -> k + "={" + k + "}").collect(Collectors.toList()));
        url = url + param;
        log.info("unbind url " + url);
        ResponseResult response = restTemplate.getForObject(url, ResponseResult.class, map);
        log.info("unbind response " + response);
    }

    public BoundPhoneDto bind(String phone1, String phone2, long expireTime) {
        String url = configService.fileServiceUrl() + "/phone/bind?";
        Map<String, Object> map = new HashMap<>();
        map.put("driverPhone", phone1);
        map.put("passengerPhone", phone2);
        map.put("expiration", DateUtils.formatDate(new Date(expireTime), DateUtils.DEFAULT_TIME_FORMAT));
        String param = String.join("&", map.keySet().stream().map(k -> k + "={" + k + "}").collect(Collectors.toList()));
        url = url + param;
        log.info("bind " + url);
        ResponseResult response = restTemplate.getForObject(url, ResponseResult.class, map);
        log.info("bind result " + response.toString());
        if (response.getData() != null) {
            log.info(response.toString());
            try {
                BoundPhoneDto data = RestTemplateHepler.parse(response, BoundPhoneDto.class);
                return data;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean updateOrder(Order order) {
        String url = configService.orderServiceUrl() + "/order/updateOrder";
        ResponseResult response = restTemplate.postForObject(url, order, ResponseResult.class);
        if (response.getCode() == 0) {
            return true;
        }
        return false;
    }

    public ResponseResult pushMsg(PushRequest pushRequest) {
        String url = configService.messageServiceUrl() + "/push/message";
        ResponseResult response = restTemplate.postForObject(url, pushRequest, ResponseResult.class);
        return response;
    }

    public ResponseResult loopMessage(PushLoopBatchRequest request) {
        String url = configService.messageServiceUrl() + "/loop/message";
        ResponseResult response = restTemplate.postForObject(url, request, ResponseResult.class);
        return response;
    }

    public ResponseResult loopMessageBatch(PushLoopBatchRequest request) {
        String url = configService.messageServiceUrl() + "/loop/batch/message";
        ResponseResult response = restTemplate.postForObject(url, request, ResponseResult.class);
        return response;
    }

}

