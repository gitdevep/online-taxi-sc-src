package com.online.taxi.request;

import lombok.Data;

/**
 * @date 2018/08/09
 **/
@Data
public class DriverWorkStatusRequest {

    private Integer id;

    private Integer workStatus;

    private Integer csWorkStatus;

    private Integer isFollowing;

    private Integer status;

    private Double longitude;

    private Double latitude;

    private String city;

    private Double speed;

    private Double accuracy;

    private Double direction;

    private Double height;

    private Integer locationType;

    private String phoneNum;

}
