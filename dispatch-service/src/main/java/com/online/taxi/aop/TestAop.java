package com.online.taxi.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.online.taxi.config.Cache;

/**
 * @date 2018/9/13
 */
@Aspect
@Component
@Slf4j
public class TestAop {

    @Before(value = "@annotation(cache)")
    public void befor(JoinPoint joinPoint, Cache cache) {
        log.info("cache---------------------------------------++++++++++++++++++++++++++++++++++++++++++" + joinPoint.getSignature().getName());
    }
}
