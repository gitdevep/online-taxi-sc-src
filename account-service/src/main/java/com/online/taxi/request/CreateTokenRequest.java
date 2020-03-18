package com.online.taxi.request;

import lombok.Data;

/**
 * @date 2018/08/15
 **/
@Data
public class CreateTokenRequest {

    private int type;

    private String phoneNum;

    private int id;
}
