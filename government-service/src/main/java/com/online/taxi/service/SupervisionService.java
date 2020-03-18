package com.online.taxi.service;

import com.online.taxi.dto.government.SupervisionData;

/**
 * 监管上报服务
 *
 * @date 2018/8/23
 */
public interface SupervisionService {

    /**
     * 分发上报任务
     *
     * @param data 上报对象DTO
     * @throws Exception 异常信息
     */
    void dispatch(SupervisionData data) throws Exception;

}