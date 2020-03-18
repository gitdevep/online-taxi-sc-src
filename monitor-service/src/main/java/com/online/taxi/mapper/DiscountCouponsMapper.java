package com.online.taxi.mapper;

import com.online.taxi.response.DiscountCouponsResponse;
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
public interface DiscountCouponsMapper {

    /**
     * 优惠券统计
     * @param param
     * @return
     */
    List<DiscountCouponsResponse> discountCoupons(Map<String, Object> param);
}
