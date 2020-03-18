package com.online.taxi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.online.taxi.lock.RedisLock;
import com.online.taxi.service.DispatchService;

/**
 */

@Configuration
public class BeanConfig {
    @Bean
    public DispatchService dispatchService() {
        return DispatchService.ins();
    }

    @Bean
    public RedisLock redisLock() {
        return RedisLock.ins();
    }
}
