package com.online.taxi.dao;

import com.online.taxi.entity.WeixinpayCallbackInfo;
import com.online.taxi.mapper.WeixinpayCallbackInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @date 2018/9/14
 */
@Repository
public class WeixinpayCallbackInfoDao {

    @Autowired
    private WeixinpayCallbackInfoMapper weixinpayCallbackInfoMapper;

    public int insertSelective(WeixinpayCallbackInfo record) {

        return weixinpayCallbackInfoMapper.insertSelective(record);
    }
}
