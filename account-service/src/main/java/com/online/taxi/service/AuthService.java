package com.online.taxi.service;

/**
 * token服务
 * @date 2018/08/15
 **/
public interface AuthService {

    /**
     * 生成验证码
     * @param phoneNum 手机号
     * @return string
     */

    String createToken(String phoneNum);

    /**
     * 检查验证码
     * @param token token
     * @return string
     */
    String checkToken(String token);

    /**
     * 删除token
     * @param subject subject
     */
    void deleteToken(String subject);
}
