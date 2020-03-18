package com.online.taxi.mapper;

import com.online.taxi.entity.PushMessageRecord;

/**
 */
public interface PushMessageRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(PushMessageRecord record);

    PushMessageRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PushMessageRecord record);

}