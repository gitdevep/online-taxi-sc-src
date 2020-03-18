package com.online.taxi.mapper;

import com.online.taxi.response.UserResponse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 功能描述
 *
 * @date 2018/10/15
 */
@Mapper
@Service
public interface UserStatisticsMapper {

    /**
     * 注册用户数
     * @param param
     * @return
     */
    List<UserResponse> selectRegUserStatistics(Map<String, Object> param);

    /**
     * 下单用户数
     * @param param
     * @return
     */
    List<UserResponse> placeAnOrderUserNumber(Map<String, Object> param);

    /**
     * 活跃用户数
     * @param param
     * @return
     */
    List<UserResponse> activeUserNumber(Map<String, Object> param);
}
