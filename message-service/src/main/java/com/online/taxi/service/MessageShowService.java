package com.online.taxi.service;

/**
 * @date 2018/9/8
 */
public interface MessageShowService {

    int saveMessageShow(String title, String content, String yid, int acceptIdentity, int pushType);
}
