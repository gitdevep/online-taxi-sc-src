package com.online.taxi.mapper;

import com.online.taxi.entity.SmsTemplate;
import org.springframework.stereotype.Service;

/**
 */
@Service
public interface SmsTemplateMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SmsTemplate record);

    int insertSelective(SmsTemplate record);

    SmsTemplate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SmsTemplate record);

    int updateByPrimaryKey(SmsTemplate record);

    SmsTemplate selectByTemplateId(String templeId);
}
