package com.online.taxi.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.online.taxi.constant.SmsEnum;
import com.online.taxi.dao.SmsDao;
import com.online.taxi.dao.SmsTemplateDao;
import com.online.taxi.dto.SmsSendRequest;
import com.online.taxi.dto.SmsTemplateDto;
import com.online.taxi.entity.Sms;
import com.online.taxi.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    /**
     *产品名称:云通信短信API产品,开发者无需替换
     */
    private final String product = "Dysmsapi";
    /**
     * 产品域名,开发者无需替换
     */
    private final String domain = "dysmsapi.aliyuncs.com";

    @Value("${ali.sms.key-id}")
    private String accessKeyId;

    @Value("${ali.sms.key-secret}")
    private String accessKeySecret;

    @Value("${ali.sms.sign}")
    private String sign;

    @Autowired
    private SmsTemplateDao smsTemplateDao;

    @Autowired
    private SmsService smsService;

    @Autowired
    private SmsDao smsDao;

    public int sendMsg(String phoneNumber, String templateCode, String param) {
        try {
            //可自助调整超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");

            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(phoneNumber);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(sign);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(templateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            request.setTemplateParam(param);
            //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");

            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId("yourOutId");

            //hint 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            log.info("短信结果："+JSONObject.fromObject(sendSmsResponse));
            // 解析发送结果
            if (sendSmsResponse != null && sendSmsResponse.getCode().trim().equals("OK")) {
                System.out.println(sendSmsResponse.getBizId());
                log.info("阿里短信通道 成功 - 手机号码 - " + phoneNumber + " 内容 " + param);
                return SmsEnum.OK.getCode();
            } else {
                log.info("阿里短信通道 失败 - 手机号码 - " + phoneNumber + " 内容 " + param);
                return SmsEnum.FAIL.getCode();
            }
        }catch (Exception e){
            return SmsEnum.EXCEPTION.getCode();
        }

    }

    // 缓存用于替换内容的模板
    private Map<String, String> templateMaps = new HashMap<>();

    @Override
    public int sendSms(SmsSendRequest request) {
        log.info(request.toString());

        for (String phoneNumber : request.getReceivers()) {
            List<SmsTemplateDto> templates = request.getData();

            Sms sms = new Sms();
            sms.setPassengerPhoneNumber(phoneNumber);

            for (SmsTemplateDto template : templates) {
                // 加载模板内容至缓存
                if (!templateMaps.containsKey(template.getId())) {
                    templateMaps.put(template.getId(),
                            smsTemplateDao.findByTemplateId(template.getId()).getContent());
                }

                // 替换占位符
                String content = templateMaps.get(template.getId());
                for (Map.Entry<String, Object> entry : template.getTemplateMap().entrySet()) {
                    content = StringUtils.replace(content, "${" + entry.getKey() + "}", "" + entry.getValue());
                }

                // 发生错误时，不影响其他手机号和模板的发送
                try {
                    int result = send(phoneNumber, template.getId(), template.getTemplateMap());

                    // 组装SMS对象
                    sms.setSendTime(new Date());
                    sms.setOperator("");
                    sms.setSendFlag(1);
                    sms.setSendNumber(0);
                    sms.setSmsContent(content);

                    if (result != SmsEnum.OK.getCode()) {
                        throw new Exception("短信发送失败");
                    }
                } catch (Exception e) {
                    sms.setSendFlag(0);
                    sms.setSendNumber(1);
                    log.error("发送短信（" + template.getId() + "）失败：" + phoneNumber, e);
                } finally {
                    sms.setCreateTime(new Date());
                    smsDao.insert(sms);
                }
            }
        }
        return 0;
    }

    private int send(String phoneNumber, String templateId, Map<String, ?> map) throws Exception {
        JSONObject param = new JSONObject();
        param.putAll(map);

        return sendMsg(phoneNumber, templateId, param.toString());
    }
}
