package com.online.taxi.mapper;

import com.online.taxi.entity.PushAccount;

import java.util.List;

/**
 */
public interface PushAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PushAccount record);

    int insertSelective(PushAccount record);

    PushAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PushAccount record);

    int updateByPrimaryKey(PushAccount record);

    List<PushAccount> selectByIdentityAndYid(PushAccount pushAccount);
}