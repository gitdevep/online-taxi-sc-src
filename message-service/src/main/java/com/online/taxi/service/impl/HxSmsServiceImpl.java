package com.online.taxi.service.impl;

import com.online.taxi.dao.SmsDao;
import com.online.taxi.dao.SmsTemplateDao;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.sms.SmsRequest;
import com.online.taxi.entity.Sms;
import com.online.taxi.sdk.hx.SSLClient;
import com.online.taxi.service.HxSmsService;
import com.online.taxi.utils.HuaXinSmsConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 华信短信发送
 */

@Service
@Slf4j
public class HxSmsServiceImpl implements HxSmsService {

    @Autowired
    private SmsTemplateDao smsTemplateDao;

    //private Map<String, String> templateMaps = new HashMap<>();

    @Autowired
    private SmsDao smsDao;

    @Autowired
    private HuaXinSmsConfig huaXinSmsConfig;

    /**
     * 发送短信成功
     */
    private String successCode ="OK";

    @Override
    public ResponseResult sendSms(SmsRequest request) {
        log.info("request"+request);
        int i =0;
        for (String phoneNumber : request.getPhones()) {
            //if (!templateMaps.containsKey(request.getTemplateId())) {
            //    templateMaps.put(request.getTemplateId(),
            //            smsTemplateDao.findByTemplateId(request.getTemplateId()).getContent());
            //}
            //String content = templateMaps.get(request.getTemplateId());

            String content = smsTemplateDao.findByTemplateId(request.getTemplateId()).getContent();
            //判断模板中是否存在@符号
            if (content.indexOf("@") != -1){
                String[] newContent = content.split("@");
                StringBuffer stringBuffer = new StringBuffer();
                for(int j=0,len=newContent.length;j<len;j++) {
                    String contents = newContent[j];
                    if(StringUtils.isNotBlank(contents)){
                        stringBuffer.append(contents);
                        stringBuffer.append("@");
                    }
                }
                content = stringBuffer.toString().substring(0,stringBuffer.toString().length()-1);
                for (String template : request.getContent()) {
                    content = StringUtils.replaceOnce(content, "@", "" + template);
                }
            }
            log.info("短信模板内容=========="+content);
            String sendCount = send(phoneNumber,content);
            if(StringUtils.isNotBlank(sendCount)){
                i++;
            }
        }
        return ResponseResult.success("发送成功数："+i);
    }

    public String send(String phoneNumber,String content){
        String sendCount=null;
        Sms sms = new Sms();

        httpclient = SSLClient.createSSLClientDefault();
        String extNumber = "";
        String planSendTime ="";
        String sendId = "";
        String statusCode ="";
        HttpPost post = new HttpPost(huaXinSmsConfig.getWsdl());
        post.setHeader("Content-Type", "application/soap+xml; charset=UTF-8");
        try {
            String soapData = buildSoapData(huaXinSmsConfig.getUserName(), huaXinSmsConfig.getPassWord(), phoneNumber, content, extNumber, planSendTime);
            StringEntity se = new StringEntity(soapData, "UTF-8");
            post.setEntity(se);
            HttpResponse response = httpclient.execute(post);

            HttpEntity entity = response.getEntity();
            // 将字符转化为XML
            String returnString = EntityUtils.toString(entity, "UTF-8");
            SOAPMessage msg = formatSoapString(returnString);
            msg.writeTo(System.out);
            SOAPBody body = msg.getSOAPBody();
            Map<String, String> map = new HashMap<String, String>(15);
            Iterator<SOAPElement> iterator = body.getChildElements();
            parseSoap(iterator, map);

            sendId =  map.get("MsgId");
            statusCode = map.get("StatusCode");
            if(successCode.equals(statusCode)){
                sendCount = map.get("SuccessCounts");
            }

            sms.setPassengerPhoneNumber(phoneNumber);
            sms.setSendTime(new Date());
            sms.setOperator("");
            sms.setSendFlag(1);
            sms.setSendNumber(0);
            sms.setSmsContent(content);
            if (!successCode.equals(statusCode)) {
                throw new Exception("短信发送失败");
            }
        } catch (Exception e) {
            sms.setSendTime(new Date());
            sms.setSendFlag(0);
            sms.setSendNumber(1);
            log.error("发送短信（" +sendId + "）失败：" + phoneNumber+"code="+statusCode, e);
        }finally {
            sms.setCreateTime(new Date());
            smsDao.insert(sms);
        }
        return sendCount;
    }

    private static CloseableHttpClient httpclient;

    public static  void main(String[] args) throws Exception {
        httpclient = SSLClient.createSSLClientDefault();
        String wsdl = "https://dx.ipyy.net/webservice.asmx?wsdl";
        //改为实际账号名
        String userName = "8M00258";
        //改为实际发送密码
        String password = "8M0025844";
        //多个手机号用“,”分隔
        String mobiles = "18911752116,13620683679";
//        String content = "【逸行出行】您的验证码为123456，10分钟内有效，如非本人操作，请忽略。";
//        String content = "【逸品出行】您收到一条预约派单，时间2018-10-25 16:20,乘客尾号2116,从西湖到北京的订单，请合理安排接乘时间。";
        String content = "【逸品出行】wo shi zhong guo ren";
        String extNumber = "";

        //定时短信需指定此字段，须UTC格式：2016-12-06T08:09:10
//	    String planSendTime="2018-10-18T10:15:10";
        String planSendTime = "";

        HttpPost post = new HttpPost(wsdl);
        post.setHeader("Content-Type", "application/soap+xml; charset=UTF-8");
        String soapData = buildSoapData(userName, password, mobiles, content, extNumber, planSendTime);
        StringEntity se = new StringEntity(soapData, "UTF-8");
        post.setEntity(se);
        HttpResponse response = httpclient.execute(post);
        try {
            HttpEntity entity = response.getEntity();
            // 将字符转化为XML
            String returnString = EntityUtils.toString(entity, "UTF-8");
            SOAPMessage msg = formatSoapString(returnString);
            msg.writeTo(System.out);
            SOAPBody body = msg.getSOAPBody();
            Map<String, String> map = new HashMap<String, String>(15);
            Iterator<SOAPElement> iterator = body.getChildElements();
            parseSoap(iterator, map);

            System.out.println("返回信息提示：" + map.get("Description"));
            System.out.println("返回状态为：" + map.get("StatusCode"));
            System.out.println("返回余额：" + map.get("Amount"));
            System.out.println("返回本次任务ID：" + map.get("MsgId"));
            System.out.println("返回成功短信数：" + map.get("SuccessCounts"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static String buildSoapData(String userName, String password, String mobiles, String content, String extNumber, String planSendTime) throws UnsupportedEncodingException {
        StringBuffer soap = new StringBuffer();

        soap.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        soap.append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">");
        soap.append("<soap12:Body>");
        soap.append("<SendSms xmlns=\"http://www.yysms.com/\">");
        soap.append("<userName>" + userName + "</userName>");
        soap.append("<password>" + password + "</password>");
        soap.append("<sms>");
        soap.append("<Msisdns>" + mobiles + "</Msisdns>");
        soap.append("<SMSContent><![CDATA[" + content + "]]></SMSContent>");
        soap.append("<ExtNumber>" + extNumber + "</ExtNumber>");
        if (!planSendTime.isEmpty()) {
            soap.append("<PlanSendTime xsi:nil='false'>" + planSendTime + "</PlanSendTime>");
        } else {
            soap.append("<PlanSendTime xsi:nil='true'>" + planSendTime + "</PlanSendTime>");
        }
        soap.append("</sms>");
        soap.append("</SendSms>");
        soap.append("</soap12:Body>");
        soap.append("</soap12:Envelope>");
        return soap.toString();
    }

    /**
     * 把soap字符串格式化为SOAPMessage
     *
     * @param soapString
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static SOAPMessage formatSoapString(String soapString) {
        MessageFactory msgFactory;
        try {
            msgFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
            SOAPMessage reqMsg = msgFactory.createMessage(new MimeHeaders(),
                    new ByteArrayInputStream(soapString.getBytes("UTF-8")));
            reqMsg.saveChanges();
            return reqMsg;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void parseSoap(Iterator<SOAPElement> iterator, Map<String, String> map) {
        while (iterator.hasNext()) {
            SOAPElement element = iterator.next();
            if ("SendSmsResult".equals(element.getParentElement().getNodeName())) {
                map.put(element.getNodeName(), element.getValue());
            }
            if (null == element.getValue() && element.getChildElements().hasNext()) {
                parseSoap(element.getChildElements(), map);
            }
        }
    }
}
