package com.online.taxi.response;

import lombok.Data;

/**
 * 功能描述
 *
 * @date 2018/10/30
 */
@Data
public class StsToken {
    private String accessKeyId;
    private String securityToken;
    private String endPoint;
    private String bucketName;
    private String accessKeySecret;
}
