package com.online.taxi.request;

import lombok.Data;

/**
 * @date 2018/08/15
 **/
@Data
public class UpdateDriverAddressRequest {

    private Integer id;

    private String phoneNumber;

    private String addressLongitude;

    private String addressLatitude;

    private String address;
}
