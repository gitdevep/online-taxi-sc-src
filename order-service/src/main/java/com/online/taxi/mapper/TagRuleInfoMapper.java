package com.online.taxi.mapper;

import com.online.taxi.dto.valuation.charging.TagPrice;
import com.online.taxi.entity.TagRuleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 标签mapper
 */
@Service
@Mapper
public interface TagRuleInfoMapper {
    /**
     * 查询标签
     * @param param
     * @return
     */
    List<TagPrice> selectByPrimaryKey(Map<String, Object> param);
}
