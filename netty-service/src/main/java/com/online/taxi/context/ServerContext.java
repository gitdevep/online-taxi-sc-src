package com.online.taxi.context;

import com.online.taxi.consts.Const;
import com.online.taxi.proto.MessageProto;
import com.online.taxi.user.User;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @date 2018/8/21
 */
@Component
@Slf4j
public class ServerContext {
    private static AtomicInteger ID = new AtomicInteger(100);
    private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    public int getNewId() {
        return ID.incrementAndGet();
    }

    public User getUserById(String id) {
        if (users.containsKey(id)) {
            return users.get(id);
        }
        return null;
    }

    public void addUser(User u) {
        users.putIfAbsent(u.getId(), u);
    }

    public void removeUser(Channel channel) {
        if (channel.attr(Const.playerKey) != null) {
            String id = channel.attr(Const.playerKey).get();
            if (users.containsKey(id)) {
                users.remove(id);
            }
        }
    }

    public void sendAll(MessageProto.RequestProto msg) {
        for (User u : users.values()) {
            u.sendMsg(msg);
        }
    }
}
