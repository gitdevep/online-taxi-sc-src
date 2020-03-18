package com.online.taxi.mapper;

import com.online.taxi.response.CarAmountResponse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 功能描述
 *
 * @date 2018/10/17
 */
@Mapper
@Service
public interface CarAmountMapper {

    /**
     * 车辆统计
     * @param param
     * @return
     */
    List<CarAmountResponse> carAmount(Map<String, Object> param);
}
