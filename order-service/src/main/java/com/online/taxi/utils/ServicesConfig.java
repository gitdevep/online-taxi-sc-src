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

    private static final String MAP_SERVER_URL = "map";
    private static final String VALUATION = "valuation";
    private static final String SECRET_KEY ="secret_key";
    private static final String MESSAGE ="message";
    private static final String FILE="file";

    /**
     * 获取服务接口地址
     *
     * @param key 接口名
     * @return 地址
     */
    public String get(String key) {
        return address.stream().filter(m -> m.containsKey(key)).findFirst().orElse(new HashMap<>(0)).get(key);
    }
    /**
     * 获取地图服务接口地址
     *
     * @return 地图服务接口地址
     */
    public String getMapAddress() {
        return get(MAP_SERVER_URL);
    }

    public String getValuation(){return get(VALUATION);}

    public String getSecretKey(){return get(SECRET_KEY);}

    public String getMessage(){return  get(MESSAGE);}

    public String getFile(){return get(FILE);}

}
