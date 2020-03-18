package com.online.taxi.service;

/**
 *
 * @date 2018/08/15
 **/
public interface DriverInfoCacheService {

    /**
     * 查询司机信息
     * @param phoneNum 手机号
     * @return string
     */
     String get(String phoneNum);

    /**
     * 保存司机信息
     * @param phoneNum 手机号
     * @param driverInfo 对象
     */
     void put(String phoneNum, String driverInfo);

}
