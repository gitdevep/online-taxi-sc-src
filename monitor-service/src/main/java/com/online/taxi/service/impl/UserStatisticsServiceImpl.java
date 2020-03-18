package com.online.taxi.service.impl;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.mapper.UserStatisticsMapper;
import com.online.taxi.request.UserRequest;
import com.online.taxi.response.UserResponse;
import com.online.taxi.service.UserStatisticsService;
import com.online.taxi.util.GetBetweenDates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述
 *
 * @date 2018/10/15
 */
@Slf4j
@Service
public class UserStatisticsServiceImpl implements UserStatisticsService {

    @Autowired
    private UserStatisticsMapper userStatisticsMapper;

    /**
     * 注册用户
     */
    private String REG_USER="1";
    /**
     * 下单用户
     */
    private String PLACE_AN_ORDER = "2";

    /**
     *
     * @param request
     * @return
     */
    @Override
    public ResponseResult userStatistics(UserRequest request) {
        List<UserResponse> userResponse = new ArrayList<>();
        Map<String, Object> param = new HashMap<>(6);
        param.put("period",request.getPeriod());
        param.put("equipment",request.getEquipment());
        param.put("check",request.getCheck());
        param.put("begin",request.getBegin());
        param.put("end",request.getEnd());
        List<String> list = new ArrayList<>();
        if(request.getPeriod().equals("1")){
            list =GetBetweenDates.getBetweenDate(request.getBegin(),request.getEnd());
        }else{
            list =GetBetweenDates.getMonthBetweenDate(request.getBegin(),request.getEnd());
        }
        param.put("list",list);
        if(REG_USER.equals(request.getCheck())){
            userResponse = userStatisticsMapper.selectRegUserStatistics(param);
        }else if(PLACE_AN_ORDER.equals(request.getCheck())){
            userResponse = userStatisticsMapper.placeAnOrderUserNumber(param);
        }else{
            userResponse = userStatisticsMapper.activeUserNumber(param);
        }
        return ResponseResult.success(userResponse);
    }
}
