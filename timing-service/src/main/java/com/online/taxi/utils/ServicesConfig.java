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
 * @date 2018/8/14
 */
@Component
@ConfigurationProperties(prefix = "services", ignoreInvalidFields = true)
@Setter
public class ServicesConfig {
    private List<Map<String, String>> address = new ArrayList<>();

    private static final String FILE="file";
    private static final String PAY ="pay";

    /**
     * 获取服务接口地址
     *
     * @param key 接口名
     * @return 地址
     */
    public String get(String key) {
        return address.stream().filter(m -> m.containsKey(key)).findFirst().orElse(new HashMap<>(0)).get(key);
    }

    public String getFile(){return get(FILE);}

    public String getPay(){return get(PAY);}

}
