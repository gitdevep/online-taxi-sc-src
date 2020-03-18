package com.online.taxi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类
 *
 * @date 2018/8/22
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
@MapperScan("com.online.taxi.mapper")
public class GovernmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GovernmentServiceApplication.class, args);
    }

}
