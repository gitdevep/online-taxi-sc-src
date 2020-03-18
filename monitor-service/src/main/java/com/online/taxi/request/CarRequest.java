package com.online.taxi.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * carRequest
 *
 * @date 2018/10/15
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CarRequest {

    /**
     * 类型：0：全部、1：运营车辆数量、2：平均运营时长
     */
    private String type;

    /**
     * 查询周期
     * 1：天、2：月
     */
    private String period;

    /**p
     * 开始时间
     */
    private String begin;

    /**
     * 结束时间
     */
    private String end;
}
