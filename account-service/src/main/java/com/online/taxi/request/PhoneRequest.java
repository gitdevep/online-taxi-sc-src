package com.online.taxi.request;

import lombok.Data;

import java.util.List;

import com.online.taxi.dto.phone.PhoneInfoView;

/**
 * @date 2018/08/15
 **/
@Data
public class PhoneRequest {

    private Integer idType;

    private List<PhoneInfoView> infoList;

}
