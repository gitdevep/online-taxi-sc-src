package com.online.taxi.listener;

import com.online.taxi.constant.IPCType;
import com.online.taxi.constatnt.QueueNames;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.service.InfoUploader;
import com.online.taxi.service.YiPinService;
import com.online.taxi.web.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.MapMessage;
import javax.jms.Message;
import java.util.Map;

/**
 * @date 2018/10/16
 */
@Service
@Slf4j
public class GovernmentUploadListener {

    private final static String KEY_IPCTYPE = "ipcType";
    private final static String KEY_JSON_VALUE = "jsonValue";
    private final static String KEY_MESSAGEMAP = "messageMap";

    @Autowired
    private ApplicationContext context;

    @Autowired
    private InfoUploader infoUploader;

    @JmsListener(destination = QueueNames.GENERAL_QUEUE)
    public void generalQueue(Message message) {
        log.info("onMessage：" + message);

        if (message instanceof MapMessage) {
            try {
                // 解析消息内容
                String ipcType = ((MapMessage) message).getString(KEY_IPCTYPE);
                Map<String, Object> obj = (Map<String, Object>) ((MapMessage) message).getObject(KEY_MESSAGEMAP);

                // 封装部级平台上报数据请求报文
                YiPinService yiPinService = (YiPinService) context.getBean(IPCType.valueOf(ipcType.toUpperCase()).getValue());
                BaseMPRequest baseMPRequest = yiPinService.execute(obj);
                log.info(ipcType + " - " + ipcType + " - 数据上报内容 -=" + baseMPRequest);
                /* 发起上报请求，HTTP返回码：成功（200，201）；失败（400，401，403，404，500，502，702，948，949，952）
                 407序号不存，554AES解密失败，555反序列化失败，200成功
                 logger.info(flag + " - " + ipcType + " - " + indexValue + " - 数据上报开始 - " +
                */
                HttpResult httpResult = infoUploader.executeByProto(baseMPRequest);
                log.info(ipcType + " - " + ipcType + " - 数据上报结束 - status=" + httpResult.getStatus() + " - data=" + httpResult.getData());
            } catch (Exception e) {
                log.warn("数据上报异常" + message, e);
            }
        }
    }

    @JmsListener(destination = QueueNames.POSITION_QUEUE)
    public void positionQueue(Message message) {
        log.info("positionQueue：" + message);

        try {
            // 解析消息内容
            String ipcType = ((MapMessage) message).getString(KEY_IPCTYPE);
            String jsonValue = ((MapMessage) message).getString(KEY_JSON_VALUE);

            // 发起上报请求，HTTP返回码：成功（200，201）；失败（400，401，403，404，500，502，702，948，949，952）
            // 407序号不存，554AES解密失败，555反序列化失败，200成功
            log.info("none - " + ipcType + " - none - 数据上报开始 - " + jsonValue);
            HttpResult httpResult = infoUploader.executePositionByProto(ipcType, jsonValue);
            log.info("none - " + ipcType + " - none - 数据上报结束 - status=" + httpResult.getStatus() + " - data="
                    + httpResult.getData());
        } catch (Exception e) {
            log.warn("数据上报异常", e);
        }
    }
}
