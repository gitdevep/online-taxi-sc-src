package com.online.taxi.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 功能描述
 *
 * @date 2018/10/15
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserRequest {
    /**
     * 查看类目
     * 1：注册用户数、2：下单用户数、3：活跃用户数
     */
    private String check;

    /**
     * 设备类目
     * 0：全部、1：ios、2：Android
     */
    private String equipment;

    /**
     * 查询周期
     * 1：天、2：月
     */
    private String period;

    /**
     * 开始时间
     */
    private String begin;

    /**
     * 结束时间
     */
    private String end;


}
