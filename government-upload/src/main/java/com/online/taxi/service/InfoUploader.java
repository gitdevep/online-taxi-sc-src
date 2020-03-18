package com.online.taxi.service;

import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.web.HttpResult;
import org.springframework.stereotype.Service;

@Service
public interface InfoUploader {

//    HttpResult execute(BaseMPRequest baseMPRequest) throws Exception ;
//
//    HttpResult execute(String url, String json) throws Exception ;

    HttpResult executeByProto(BaseMPRequest baseMPRequest) throws Exception ;

    HttpResult executePositionByProto(String ipcType, String data) throws Exception;

}
