package com.online.taxi.service.impl;

import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.online.taxi.constatnt.IdentityEnum;
import com.online.taxi.service.IdRedisService;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @date 2018/9/13
 **/
@Service
public class IdRedisServiceImpl implements IdRedisService {

    private static final String ID_KEY = "ID:";

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> vOps;

    @Override
    public String pull(Integer idType,Integer id) {
        if(ObjectUtils.nullSafeEquals(idType, IdentityEnum.PASSENGER.getCode())){
            return vOps.get("PASSENGER"+ID_KEY + id);
        }
        if(ObjectUtils.nullSafeEquals(idType, IdentityEnum.DRIVER.getCode())){
            return vOps.get("DRIVER"+ID_KEY + id);
        }
        return  null;
    }

    @Override
    public void push(Integer idType,Integer id, String phone, Integer expHours) {
        if(ObjectUtils.nullSafeEquals(idType, IdentityEnum.PASSENGER.getCode())){
            vOps.set("PASSENGER"+ID_KEY + id, phone, expHours, TimeUnit.SECONDS);
        }
        if(ObjectUtils.nullSafeEquals(idType, IdentityEnum.DRIVER.getCode())){
            vOps.set("DRIVER"+ID_KEY + id, phone, expHours, TimeUnit.SECONDS);
        }
    }
}
