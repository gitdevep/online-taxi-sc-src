package com.online.taxi.response;

import lombok.Data;

/**
 * @date 2018/08/15
 **/
@Data
public class DriverRegistResponse  {

    private String accessToken ;

    private String phoneNumber;

    private Integer gerder;

    private String driverName;

    private Integer checkStatus;

    private String headImg;

    private Integer driverId;

    private String jpushId;

    private String workStatus;

}
