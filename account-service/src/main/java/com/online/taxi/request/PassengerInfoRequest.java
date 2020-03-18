package com.online.taxi.request;

import com.online.taxi.dto.PassengerInfoView;

import lombok.Data;

/**
 * 乘客请求信息
 *
 * @date 2018/08/15
 **/
@Data
public class PassengerInfoRequest {

    private Integer id;

    private PassengerInfoView data;
}
