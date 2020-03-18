package com.online.taxi.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.online.taxi.service.DriverInfoCacheService;

import javax.annotation.Resource;

/**
 * 司机信息
 *
 * @date 2018/08/15
 **/
@Service
@Slf4j
public class DriverInfoCacheServiceImpl implements DriverInfoCacheService {

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> vOps;
    private final String DRIVER_KEY = "driver_info_";

    /**
     * 获取信息
     * @param phoneNum 手机号
     * @return string
     */
    @Override
    public String get(String phoneNum) {
        String key = DRIVER_KEY + phoneNum;
        return vOps.get(key);
    }

    /**
     * 司机缓存中状态
     * @param phoneNum 手机号
     * @param value 司机对象信息
     */
    @Override
    public void put(String phoneNum, String value) {
        String key = DRIVER_KEY + phoneNum;

        vOps.set(key, value);

        log.info("hashKey:" + key + " 缓存在Redis中的hashValue为:" + value);
    }

}
