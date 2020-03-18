package com.online.taxi.request;

import lombok.Data;

/**
 * 车辆信息
 * @date 2018/9/6
 **/
@Data
public class UpdateCarRequest {

    private Integer id;

    private Integer useStatus;

    private Integer totalMile;
}
