package com.online.taxi.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.online.taxi.entity.ChargeRule;

@Mapper
public interface ChargeRuleMapper {
    ChargeRule getChargeRule(@Param("cityCode") String cityCode, @Param("serviceTypeId") int serviceTypeId, @Param("carLevel") int carLevel);
}
