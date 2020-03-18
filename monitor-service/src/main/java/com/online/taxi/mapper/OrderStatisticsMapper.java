package com.online.taxi.mapper;

import com.online.taxi.response.OrderStatisticsResponse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 功能描述
 *
 * @date 2018/10/16
 */
@Mapper
@Service
public interface OrderStatisticsMapper {

    /**
     * 订单统计
     * @param param
     * @return
     */
    List<OrderStatisticsResponse> orderStatistics(Map<String, Object> param);

    /**
     * 流水统计
     * @param param
     * @return
     */
    List<OrderStatisticsResponse> selectOrderFlow(Map<String, Object> param);
}
