package com.online.taxi.dao;

import com.online.taxi.dto.valuation.charging.TagPrice;
import com.online.taxi.entity.ChargeRule;
import com.online.taxi.entity.ChargeRuleDetail;
import com.online.taxi.entity.TagRuleInfo;
import com.online.taxi.mapper.ChargeRuleDetailMapper;
import com.online.taxi.mapper.ChargeRuleMapper;
import com.online.taxi.mapper.TagRuleInfoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 规则操作
 **/
@Repository
@Service
public class ChargeRuleDao {

    @Autowired
    private ChargeRuleMapper chargeRuleMapper;
    @Autowired
    private ChargeRuleDetailMapper chargeRuleDetailMapper;

    @Autowired
    private TagRuleInfoMapper tagRuleInfoMapper;

    public List<ChargeRule> selectByPrimaryKey(ChargeRule chargeRule){
        return chargeRuleMapper.selectByPrimaryKey(chargeRule);
    }

    public List<ChargeRuleDetail> chargeRuleDetailList (Integer ruleId){
        return chargeRuleDetailMapper.selectByPrimaryKey(ruleId);
    }

    public List<TagPrice> selectTapInfo(Map<String, Object> param){
        return tagRuleInfoMapper.selectByPrimaryKey(param);
    }
}
