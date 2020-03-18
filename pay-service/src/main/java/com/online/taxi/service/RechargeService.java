package com.online.taxi.service;

import com.online.taxi.dto.ResponseResult;

/**
 * @date 2018/10/22
 */
public interface RechargeService {

    ResponseResult bossRecharge(Integer yid,Double capital,Double giveFee,String description,String createUser);

}
