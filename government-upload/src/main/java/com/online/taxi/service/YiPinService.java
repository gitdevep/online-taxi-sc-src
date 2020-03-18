package com.online.taxi.service;

import com.online.taxi.model.BaseMPRequest;

import java.util.Map;

public interface YiPinService {

    /**
     * 封装Json格式的结构体
     *
     * @param message
     * @return
     * @throws Exception
     */
    BaseMPRequest execute(Map<String,Object> message) throws Exception;

}