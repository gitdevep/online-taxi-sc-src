package com.online.taxi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动类
 *
 * @date 2018/8/14
 */
@SpringBootApplication
@EnableAsync
@MapperScan("com.online.taxi.mapper")
public class ValuationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValuationServiceApplication.class, args);
    }

}
