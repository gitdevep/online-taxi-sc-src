package com.online.taxi.dto;

import com.online.taxi.entity.PassengerAddress;
import com.online.taxi.entity.PassengerInfo;

import lombok.Data;

/**
 *  乘客详情信息
 * @date 2018-08-09
 **/
@Data
public class PassengerInfoView {

    private PassengerInfo passengerInfo;

    private PassengerAddress passengerAddress;


}
