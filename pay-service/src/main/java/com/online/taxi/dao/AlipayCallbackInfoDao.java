package com.online.taxi.dao;

import com.online.taxi.entity.AlipayCallbackInfo;
import com.online.taxi.mapper.AlipayCallbackInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @date 2018/9/14
 */
@Repository
public class AlipayCallbackInfoDao {

    @Autowired
    private AlipayCallbackInfoMapper alipayCallbackInfoMapper;

    public int insertSelective(AlipayCallbackInfo record) {
        return alipayCallbackInfoMapper.insertSelective(record);
    }
}
