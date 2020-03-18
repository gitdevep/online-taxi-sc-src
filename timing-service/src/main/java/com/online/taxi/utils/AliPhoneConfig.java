package com.online.taxi.utils;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 功能描述
 *
 * @date 2018/9/19
 */
@Component
@ConfigurationProperties(prefix = "apikey", ignoreInvalidFields = true)
@Setter
public class AliPhoneConfig {

    private List<Map<String, String>> ali = new ArrayList<>();

    private static final String POOL_KEY = "pool_key";
    private static final String PRODUCT = "dyplsapi";
    private static final String DOMAIN = "domain";
    public static final String KEY = "key_id";
    private static final String SECRET = "key_secret";
    private static final String FILE_PATH ="file_path";

    public String get(String key) {
        return ali.stream().filter(m -> m.containsKey(key)).findFirst().orElse(new HashMap<>(0)).get(key);
    }

    public String getPoolKey(){return get(POOL_KEY);}
    public String getProduct(){return get(PRODUCT);}
    public String getDomain(){return get(DOMAIN);}
    public String getKey(){return get(KEY);}
    public String getSecret(){return get(SECRET);}
    public String getFilePath(){return get(FILE_PATH);}

}
