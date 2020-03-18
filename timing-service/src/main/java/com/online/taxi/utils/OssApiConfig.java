package com.online.taxi.utils;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述
 *
 * @date 2018/8/21
 */
@Component
@ConfigurationProperties(prefix = "apikey", ignoreInvalidFields = true)
@Setter
public class OssApiConfig {

    private List<Map<String, String>> oss = new ArrayList<>();
    private static final String ENDPOINT ="endpoint";
    private static final String ACCESS_ID ="access_id";
    private static final String ACCESS_KEY="access_key";
    private static final String BUCKET_LKMOTION ="bucket_lkmotion";
    private static final String OSS_FILE_NAME = "oss_file_name";
    private static final String ROLE_ARN = "role_arn";


    public String get(String key) {
        return oss.stream().filter(m -> m.containsKey(key)).findFirst().orElse(new HashMap<>(0)).get(key);
    }
    public String getEndpoint(){return get(ENDPOINT);}
    public String getAccessId(){return get(ACCESS_ID);}
    public String getAccessKey(){return get(ACCESS_KEY);}
    public String getBucketLkmotion(){return get(BUCKET_LKMOTION);}
    public String getOssFileName(){return get(OSS_FILE_NAME);}

    public String getRoleArn() {
        return get(ROLE_ARN);
    }

}
