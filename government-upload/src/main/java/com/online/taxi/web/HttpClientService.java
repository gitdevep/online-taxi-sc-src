package com.online.taxi.web;

import java.util.Map;

/**
 * @date 2018/10/16
 */
public interface HttpClientService {

    String doGet(String url) throws Exception;

    String doGet(String url, Map<String, String> paramMap)
            throws Exception;

    HttpResult doPost(String url, Map<String, String> paramMap)
            throws Exception;

    HttpResult doPost(String url, byte[] bytes) throws Exception;

	HttpResult doPostT(String url, byte[] bytes);
}