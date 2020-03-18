package com.online.taxi.dto;

import lombok.Data;

/**
 * 功能描述
 *
 * @date 2018/8/31
 */
@Data
public class BaseInfoDto {
    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 服务名称
     */
    private String serviceTypeName;

    /**
     * 车辆级别名称
     */
    private String carLevelName;
}
