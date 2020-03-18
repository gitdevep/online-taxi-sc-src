package com.online.taxi.mapper;

import com.online.taxi.entity.OrderRuleMirror;
import org.springframework.stereotype.Service;

/**
 */
@Service
public interface OrderRuleMirrorMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrderRuleMirror record);

    int insertSelective(OrderRuleMirror record);

    OrderRuleMirror selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderRuleMirror record);

    int updateByPrimaryKeyWithBLOBs(OrderRuleMirror record);

    int updateByPrimaryKey(OrderRuleMirror record);

}
