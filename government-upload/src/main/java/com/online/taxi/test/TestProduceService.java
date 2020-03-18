package com.online.taxi.test;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @date 2018/10/16
 */
@Service
public class TestProduceService {

    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    public String produce(String content) {
        ActiveMQQueue activeMQQueue = new ActiveMQQueue("testQueue");
        jmsTemplate.convertAndSend(activeMQQueue, content);
        return "生产内容：" + content + " 成功";
    }
}
