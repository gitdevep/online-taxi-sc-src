package com.online.taxi.service;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.request.PhoneRequest;

/**
 * @date 2018/9/5
 **/
public interface PhoneService {

    ResponseResult getDecryptById(PhoneRequest request);

    ResponseResult createEncrypt(PhoneRequest request);

    ResponseResult getPhoneByEncryptList(PhoneRequest request);
}
