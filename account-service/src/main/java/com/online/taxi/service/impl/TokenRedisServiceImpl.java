package com.online.taxi.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.online.taxi.service.TokenRedisService;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @date 2018/08/15
 **/
@Service
public class TokenRedisServiceImpl implements TokenRedisService {

    private static final String PRE_KEY = "token:";

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> vOps;

    @Resource(name = "redisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void put(String phoneNum, String token, Integer expHours) {
        vOps.set(PRE_KEY + phoneNum, token, expHours, TimeUnit.HOURS);
    }

    @Override
    public String get(String phoneNum) {
        return vOps.get(PRE_KEY + phoneNum);
    }

    @Override
    public Boolean expire(String phoneNum, Integer expHours) {
        return redisTemplate.expire(phoneNum, expHours, TimeUnit.HOURS);
    }

    @Override
    public void delete(String phoneNum) {
        redisTemplate.delete(PRE_KEY + phoneNum);
    }


}
