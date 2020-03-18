package com.online.taxi.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.request.PhoneRequest;
import com.online.taxi.service.PhoneService;

/**
 * 手机号控制层
 * @date 2018/08/10
 **/
@RestController
@RequestMapping("/phone")
@RequiredArgsConstructor
public class PhoneController {

    @NonNull
    private PhoneService phoneService;

    /**
     * 根据ID解密手机号
     * @param request PhoneRequest对象
     * @return ResponseResult实例
     */
    @PostMapping(value = "/getPhoneList")
    public ResponseResult getPhoneByIdList(@RequestBody PhoneRequest request) {

        return phoneService.getDecryptById(request);
    }

    /**
     * 加密手机号
     * @param request PhoneRequest对象
     * @return ResponseResult实例
     */
    @PostMapping(value = "/createEncrypt")
    public ResponseResult createEncrypt(@RequestBody PhoneRequest request) {

        return phoneService.createEncrypt(request);
    }

    /**
     * 根据加密手机号解密
     * @param request PhoneRequest对象
     * @return ResponseResult实例
     */
    @PostMapping(value = "/getPhoneByEncryptList")
    public ResponseResult getPhoneByEncryptList(@RequestBody PhoneRequest request) {

        return phoneService.getPhoneByEncryptList(request);
    }
}
