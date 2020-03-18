package com.online.taxi.service;

import com.online.taxi.dto.map.Distance;
import com.online.taxi.dto.map.Points;

/**
 */
public interface RouteService {
    /**
     * 查询指定车辆，在某个时间段内的里程
     * @param carId
     * @param city
     * @param startTime
     * @param endTime
     * @return
     */
	Distance getRoute(String carId,String city ,Long startTime, Long endTime);
    
    /**
     * 查询指定车辆，在某个时间段内轨迹集合
     * @param carId
     * @param city
     * @param startTime
     * @param endTime
     * @return
     */
    Points getPointsAllPage(String carId, String city, Long startTime, Long endTime, String correction);


}
