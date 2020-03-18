package com.online.taxi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.taxi.dto.government.SupervisionData;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.util.NumberUtils;

import java.util.function.Supplier;

/**
 * JMS控制器
 *
 * @date 2018/8/29
 */
public abstract class AbstractJmsController {

    @Autowired
    private ActiveMQQueue bufferQueue;

    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    /**
     * 生产并发送json
     *
     * @param supplier json字符串的生产者
     */
    private void composeAndSend(Supplier<String> supplier) {
        jmsTemplate.convertAndSend(bufferQueue, supplier.get());
    }

    /**
     * 触发监听器
     *
     * @param clazz 要出发的类
     * @param id    主键
     * @throws Exception 异常
     */
    protected void triggerListener(Class clazz, String id, String operation) throws Exception {
        int dataId = NumberUtils.parseNumber(id, Integer.class);
        SupervisionData.OperationType operationType = SupervisionData.OperationType.valueOf(operation.toUpperCase());
        SupervisionData data = new SupervisionData().setClassName(clazz.getName()).setId(dataId).setOperationType(operationType);
        String json = new ObjectMapper().writeValueAsString(data);
        composeAndSend(() -> json);
    }
}
