package com.online.taxi.dao;

import com.online.taxi.entity.Sms;
import com.online.taxi.mapper.SmsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 */
@Repository
public class SmsDao {
    @Autowired
    private SmsMapper smsMapper;

    public int insert(Sms sms){
        return smsMapper.insertSelective(sms);
    }
}
