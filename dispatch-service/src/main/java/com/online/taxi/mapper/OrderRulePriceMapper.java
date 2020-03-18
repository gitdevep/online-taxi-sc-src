package com.online.taxi.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.online.taxi.entity.OrderRulePrice;

@Mapper
@Service
public interface OrderRulePriceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderRulePrice record);

    int insertSelective(OrderRulePrice record);

    OrderRulePrice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderRulePrice record);

    int updateByPrimaryKey(OrderRulePrice record);

    OrderRulePrice selectByOrderId(@Param("orderId") int orderId);
}
