package com.online.taxi.consts;

import io.netty.util.AttributeKey;

/**
 * @date 2018/8/20
 */
public class Const {
    public static final String SERVER_HOST = "127.0.0.1";

    public static final int SERVER_PORT = 80;

    public static AttributeKey<String> playerKey = AttributeKey.newInstance("player_key");
}
