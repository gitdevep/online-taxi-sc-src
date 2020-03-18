package com.online.taxi.request;

import lombok.Data;

/**
 * 获取请求token
 * @date 2018/09/05
 **/
@Data
public class GetTokenRequest  {

    private String phoneNum;

    private String verifyCode;

    private Integer id;

    private Integer type;

    private Integer identityStatus;

    private String equipType;

    private Double longitude;

    private Double latitude;

    private Double speed;

    private Double accuracy;

    private Double direction;

    private Double height;

    private String city;

    private String token;

    private String registerSource;

    private String marketChannel;

}
