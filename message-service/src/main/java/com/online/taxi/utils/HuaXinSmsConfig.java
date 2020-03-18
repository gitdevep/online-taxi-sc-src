package com.online.taxi.utils;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 功能描述
 *
 * @date 2018/10/23
 */
@Component
@ConfigurationProperties(prefix = "huaxin", ignoreInvalidFields = true)
@Setter
public class HuaXinSmsConfig {

    private Map<String, String> sms = new LinkedHashMap<>();

    private static final String WSDL ="wsdl";
    private static final String USER_NAME = "user-name";
    private static final String PASS_WORD = "pass-word";

    public String get(String key) {
        return sms.get(key);
    }

    public String getWsdl(){return get(WSDL);}
    public String getUserName(){return get(USER_NAME);}
    public String getPassWord(){return get(PASS_WORD);}
}
