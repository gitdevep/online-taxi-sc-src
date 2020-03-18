package com.online.taxi.dao;

import com.online.taxi.entity.PushMessageRecord;
import com.online.taxi.mapper.PushMessageRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 */
@Repository
public class PushMessageRecordDao {

    @Autowired
    private PushMessageRecordMapper pushMessageRecordMapper;

    public int insert(PushMessageRecord pushMessageRecord){
        return pushMessageRecordMapper.insertSelective(pushMessageRecord);
    }
}
