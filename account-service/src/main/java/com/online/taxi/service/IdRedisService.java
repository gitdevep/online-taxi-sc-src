package com.online.taxi.service;

/**
 * @date 2018/9/13
 **/
public interface IdRedisService {

    String pull(Integer idType,Integer id);

    void push(Integer idType,Integer id , String phone,Integer expHours);
}
