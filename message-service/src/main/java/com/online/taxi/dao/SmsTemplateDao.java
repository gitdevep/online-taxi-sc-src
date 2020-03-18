package com.online.taxi.dao;

import com.online.taxi.entity.SmsTemplate;
import com.online.taxi.mapper.SmsTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 */
@Repository
public class SmsTemplateDao {

    @Autowired
    private SmsTemplateMapper smsTemplateMapper;

    public SmsTemplate findByTemplateId(String templateId) {
        return smsTemplateMapper.selectByTemplateId(templateId);
    }
}
