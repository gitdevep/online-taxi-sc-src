package com.online.taxi.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 响应
 * @date 2018/08/15
 **/
@Data
public class PassengerRegistResponse  {

    private String status;

    private String accessToken ;

    private String phoneNum;

    private Byte gender;

    private Integer id;

    private String passengerName;

    private BigDecimal balance;

    private String headImg;

    private String jpushId;

    private int lastLoginMethod;

    private Date LastLoginTime;

    private Integer isContact;

    private Integer isShare;

    private String sharingTime;

    private Long birthday;

    private Integer isNewer;

}
