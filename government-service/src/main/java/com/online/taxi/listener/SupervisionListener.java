package com.online.taxi.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.taxi.constatnt.QueueNames;
import com.online.taxi.dto.government.SupervisionData;
import com.online.taxi.service.SupervisionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 监管上报总监听器
 *
 * @date 2018/8/24
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SupervisionListener {

    @NonNull
    private SupervisionService supervisionService;

    /**
     * 监听
     *
     * @param json 上报消息
     */
    @JmsListener(destination = QueueNames.BUFFER_QUEUE)
    public void receiveQueue(String json) {
        try {
            log.info("上报消息={}", json);
            ObjectMapper mapper = new ObjectMapper();
            SupervisionData data = mapper.readValue(json, SupervisionData.class);
            supervisionService.dispatch(data);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Supervision发生异常：", e);
        }
    }

}
