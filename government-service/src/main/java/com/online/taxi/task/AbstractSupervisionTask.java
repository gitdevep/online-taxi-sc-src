package com.online.taxi.task;

import com.online.taxi.data.upload.proto.OTIpcDef;
import com.online.taxi.exception.ParameterException;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.jms.MapMessage;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * 上报任务的基类
 *
 * @date 2018/8/24
 */
@Slf4j
public abstract class AbstractSupervisionTask implements SupervisionTask {
    /**
     * 监听到插入操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    public abstract boolean insert(Integer id);

    /**
     * 监听到更新操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    public abstract boolean update(Integer id);

    /**
     * 监听到删除操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    public abstract boolean delete(Integer id);

    /**
     * 数据上报
     */
    public void send() {
        if (null == ipcType) {
            throw new ParameterException("ipcType为空");
        }

        try {
            switch (ipcType) {
                case positionDriver:
                case positionVehicle:
                    sendPosition();
                    break;

                default:
                    sendGeneral();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("JMS发送异常：", e);
        }
    }

    /**
     * 设置共同消息
     */
    private void setCommonContent() {
        //注册地行政区划代码
        //杭州：110100
        //长沙：430100
        messageMap.put("Address", 430100);
    }

    /**
     * 获取解密后的电话号码
     *
     * @param phoneNumber 加密的电话号码
     * @return 解密后的电话号码
     */
    @SneakyThrows
    protected String getPhoneNumber(String phoneNumber) {
        byte[] keyBytes = PHONE_KEY.getBytes(StandardCharsets.UTF_8);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(keyBytes)), new IvParameterSpec(keyBytes));
        String num = URLDecoder.decode(new String(cipher.doFinal(convertHexString(phoneNumber))), StandardCharsets.UTF_8.name());
        if (StringUtils.isEmpty(num)) {
            throw new RuntimeException(String.format("phoneNumber：%s 解密失败", phoneNumber));
        }
        return num;
    }

    /**
     * 发送一般信息
     */
    private void sendGeneral() {
        setCommonContent();
        jmsTemplate.send(generalQueue, session -> {
            MapMessage message = session.createMapMessage();
            message.setString("ipcType", ipcType.name());
            message.setObject("messageMap", messageMap);
            return message;
        });
        log.info("JMS发送ipcType={}, data={}", ipcType, messageMap);
    }

    /**
     * 发送行驶轨迹
     */
    private void sendPosition() {
        if (StringUtils.isEmpty(gpsValues)) {
            throw new ParameterException("gpsValues为空");
        }

        jmsTemplate.send(positionQueue, session -> {
            MapMessage message = session.createMapMessage();
            message.setString("ipcType", ipcType.name());
            message.setString("jsonValue", gpsValues);
            return message;
        });
        log.info("JMS发送ipcType={}, gpsValues={}", ipcType, gpsValues);
    }

    protected boolean tryComposeData(int maxTimes, Predicate<Integer> predicate) {
        for (int i = 1; i <= maxTimes; i++) {
            if (predicate.test(i)) {
                return true;
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static byte[] convertHexString(String ss) {
        byte[] digest = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }
        return digest;
    }

    /**
     * 存放消息
     */
    protected final Map<String, Object> messageMap = new HashMap<>();

    /**
     * GPS消息
     */
    protected String gpsValues;

    /**
     * 消息类型
     */
    protected OTIpcDef.IpcType ipcType;

    /**
     * 公司（/平台）标识，部平台统一分配网约车平台公司标识
     */
    protected String companyId = "3301YPZCX78Q";

    /**
     * 电话号码加密解密KEY
     */
    private static final String PHONE_KEY = "pio-tech";

    /**
     * 默认重试次数
     */
    final protected int maxTimes = 1;

    @Autowired
    private ActiveMQQueue generalQueue;

    @Autowired
    private ActiveMQQueue positionQueue;

    @Autowired
    private JmsTemplate jmsTemplate;

}
