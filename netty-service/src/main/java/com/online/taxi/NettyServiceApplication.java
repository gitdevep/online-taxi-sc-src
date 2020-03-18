package com.online.taxi;

import com.online.taxi.server.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @date 2018/8/20
 */
@SpringBootApplication
public class NettyServiceApplication implements CommandLineRunner {
    @Autowired
    private NettyServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(NettyServiceApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        nettyServer.start();
    }
}
