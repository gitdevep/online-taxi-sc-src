package com.online.taxi.service;

/**
 * @date 2018/08/15
 **/
public interface TokenRedisService {

     void put(String phoneNum, String token, Integer expHours);

     String get(String phoneNum);

     Boolean expire(String phoneNum, Integer expHours);

     void delete(String phoneNum);


}
