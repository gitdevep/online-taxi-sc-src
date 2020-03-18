package com.online.taxi.test;

import org.springframework.jms.annotation.JmsListener;

/**
 * @date 2018/10/16
 */
//@Service
public class TestConsumeService {

    @JmsListener(destination = "testQueue")
    public void consume(String content){
        System.out.println("消费消息，内容："+content);
    }
}
