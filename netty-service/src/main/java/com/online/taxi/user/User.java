package com.online.taxi.user;

import io.netty.channel.Channel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @date 2018/8/20
 */
@Slf4j
@Data
public class User {
    private String id;
    private Channel channel;

    public void sendMsg(Object msg) {
        channel.writeAndFlush(msg);
    }
}
