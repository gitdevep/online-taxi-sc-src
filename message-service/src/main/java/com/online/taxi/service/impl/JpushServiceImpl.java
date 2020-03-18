package com.online.taxi.service.impl;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.online.taxi.constant.JpushConfig;
import com.online.taxi.constant.JpushEnum;
import com.online.taxi.constatnt.AudienceEnum;
import com.online.taxi.constatnt.BusinessInterfaceStatus;
import com.online.taxi.constatnt.IdentityEnum;
import com.online.taxi.constatnt.PlatformEnum;
import com.online.taxi.dao.PushAccountDao;
import com.online.taxi.dao.PushMessageRecordDao;
import com.online.taxi.dto.JpushInfo;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.push.JpushMessage;
import com.online.taxi.dto.push.PushRequest;
import com.online.taxi.entity.PushAccount;
import com.online.taxi.entity.PushMessageRecord;
import com.online.taxi.service.JpushService;
import com.online.taxi.service.MessageShowService;
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
public class JpushServiceImpl implements JpushService {

    @Value("${jpush.passenger.master-secret}")
    private String passengerMasterSecret;

    @Value("${jpush.passenger.app-key}")
    private String passengerAppKey;

    @Value("${jpush.largeScreen.master-secret}")
    private String largeScreenMasterSecret;

    @Value("${jpush.largeScreen.app-key}")
    private String largeScreenAppKey;

    @Value("${jpush.driver.master-secret}")
    private String driverMasterSecret;

    @Value("${jpush.driver.app-key}")
    private String driverAppKey;

    @Value("${jpush.carScreen.master-secret}")
    private String carScreenMasterSecret;

    @Value("${jpush.carScreen.app-key}")
    private String carScreenAppKey;

    @Autowired
    private PushMessageRecordDao pushMessageRecordDao;

    @Autowired
    private  PushAccountDao pushAccountDao;

    @Autowired
    private MessageShowService messageShowService;

    @Override
    public ResponseResult sendSingleJpushToApp(PushRequest pushRequest,int channelType){
        int acceptIdentity = pushRequest.getAcceptIdentity();
        String acceptId = pushRequest.getAcceptId();

        List<PushAccount> pushAccounts = pushAccountDao.selectByIdentityAndYid(acceptIdentity,acceptId);
        PushAccount pushAccount;
        if(!pushAccounts.isEmpty()){
            pushAccount = pushAccounts.get(0);
        } else {
            return ResponseResult.fail(JpushEnum.PUSH_ACCOUNT_EMPTY.getValue());
        }
        String source = pushAccount.getSource();
        String jpushId = pushAccount.getJpushId();
        Integer audience = pushAccount.getAudience();

        String title = pushRequest.getTitle();
        int messageType = pushRequest.getMessageType();
        String messageBody = pushRequest.getMessageBody();
        JpushMessage jpushMessage = new JpushMessage();
        jpushMessage.setTitle(title);
        jpushMessage.setMessageType(messageType);
        jpushMessage.setMessageBody(messageBody);

        if((!PlatformEnum.IOS.getValue().equalsIgnoreCase(source)) && (!PlatformEnum.ANDROID.getValue().equalsIgnoreCase(source))) {
            return ResponseResult.fail(JpushEnum.PLATFORM_ERROR.getCode(),JpushEnum.PLATFORM_ERROR.getValue());
        }
        if ((AudienceEnum.REGISTRATION_ID.getCode() != audience) && (AudienceEnum.ALIAS.getCode() != audience)) {
            return ResponseResult.fail(JpushEnum.AUDIENCE_ERROR.getCode(), JpushEnum.AUDIENCE_ERROR.getValue());
        }

        if(channelType == JpushConfig.CHANNEL_MESSAGE){

            ResponseResult responseResult = sendSingleMessage(acceptIdentity, source, jpushId, jpushMessage, audience, pushRequest.getSendId(), pushRequest.getSendIdentity());

            try {
                log.info("保存业务消息：" + JSONObject.fromObject(pushRequest));
                saveBusinessMessage(responseResult, pushRequest);
            } catch (Exception e) {
                log.info("保存业务消息失败" + e);
            }

            return responseResult;
        }

        if(channelType == JpushConfig.CHANNEL_NOTICE){
            ResponseResult responseResult = sendSingleNotice(acceptIdentity, source, jpushId, jpushMessage, audience, pushRequest.getSendId(), pushRequest.getSendIdentity());
            try {
                log.info("保存业务消息：" + JSONObject.fromObject(pushRequest));
                saveBusinessMessage(responseResult, pushRequest);
            } catch (Exception e) {
                log.info("保存业务消息失败" + e);
            }
            return responseResult;
        }

        return ResponseResult.fail(JpushEnum.PUSH_CHANNEL_EMPTY.getCode(),JpushEnum.PUSH_CHANNEL_EMPTY.getValue());
    }

    private ResponseResult sendSingleMessage(int pushAccountIdentity, String targetPlatform, String jpushId, JpushMessage jpushMessage, int audience, String sendId, Integer sendIdentity) {
        int sendResult = 0;

        String appKey = "";
        String masterSecret = "";
        JpushInfo jpushInfo = getJpushInfo(pushAccountIdentity);
        if (null == jpushInfo) {
            return ResponseResult.fail(JpushEnum.IDENTITY_EMPTY.getCode(), JpushEnum.IDENTITY_EMPTY.getValue());
        } else {
            appKey = jpushInfo.getAppKey();
            masterSecret = jpushInfo.getMasterSecret();
        }
        JPushClient jpushClient = null;
        try {
            jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
            PushResult pr;

            PushPayload pushPayload = buildMessagePushPayLoad(jpushMessage.getTitle(),jpushMessage.getMessageBody(),jpushId,targetPlatform,audience);
            if (pushPayload == null){
                throw new Exception("pushPayLoad 构建为空");
            }
            jpushClient.sendPush(pushPayload);

            sendResult = 1;
        } catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            sendResult = 0;
            return ResponseResult.fail(JpushEnum.EXCEPTION.getCode(),JpushEnum.EXCEPTION.getValue());
        }finally {
            jpushClient.close();
            insertPushMessageRecord(sendResult, jpushId, jpushMessage.getMessageBody(), jpushMessage.getMessageType(), targetPlatform, sendId, sendIdentity);

        }
        return ResponseResult.fail(JpushEnum.OK.getCode(),JpushEnum.OK.getValue());
    }

    private ResponseResult sendSingleNotice(int pushAccountIdentity , String targetPlatform , String jpushId ,
                                             JpushMessage jpushMessage , int audience ,String sendId,
                                            Integer sendIdentity) {

        //发送结果
        int sendResult = 0;
        try {
            String appKey = "";
            String masterSecret = "";
            JpushInfo jpushInfo = getJpushInfo(pushAccountIdentity);
            if (null == jpushInfo){
                return ResponseResult.fail(JpushEnum.IDENTITY_EMPTY.getCode(),JpushEnum.IDENTITY_EMPTY.getValue());
            }else {
                appKey = jpushInfo.getAppKey();
                masterSecret = jpushInfo.getMasterSecret();
            }
            Map<String,String> map = new HashMap<>(1);
            map.put("messageType",jpushMessage.getMessageType()+"");
            map.put("messageBody",jpushMessage.getMessageBody());

            JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
            PushResult pr;

            PushPayload pushPayload = buildNoticePushPayLoad(jpushMessage.getTitle(),map,jpushId,targetPlatform,audience);
            if (pushPayload == null){
                throw new Exception("pushPayLoad 构建为空");
            }
            jpushClient.sendPush(pushPayload);
            sendResult = 1;
        } catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            sendResult = 0;
            return ResponseResult.fail(JpushEnum.EXCEPTION.getCode(),JpushEnum.EXCEPTION.getValue());
        }finally {
            insertPushMessageRecord(sendResult,jpushId,jpushMessage.getMessageBody(),jpushMessage.getMessageType(),targetPlatform,sendId,sendIdentity);
        }
        return ResponseResult.fail(JpushEnum.OK.getCode(),JpushEnum.OK.getValue());
    }

    private JpushInfo getJpushInfo(int pushAccountIdentity){
        JpushInfo jpushInfo = new JpushInfo();
        String appKey = "";
        String masterSecret = "";
        if (pushAccountIdentity == IdentityEnum.PASSENGER.getCode()){
            appKey = passengerAppKey;
            masterSecret = passengerMasterSecret;
        }else if(pushAccountIdentity == IdentityEnum.DRIVER.getCode()){
            appKey = driverAppKey;
            masterSecret = driverMasterSecret;
        }else if(pushAccountIdentity == IdentityEnum.LARGE_SCREEN.getCode()){
            appKey = largeScreenAppKey;
            masterSecret = largeScreenMasterSecret;
        }else if(pushAccountIdentity == IdentityEnum.CAR_SCREEN.getCode()){
            appKey = carScreenAppKey;
            masterSecret = carScreenMasterSecret;
        }else {
            return null;
        }
        jpushInfo.setAppKey(appKey);
        jpushInfo.setMasterSecret(masterSecret);
        return jpushInfo;
    }

    private void insertPushMessageRecord(int sendResult,String jpushId,String messageBody,int messageType,String targetPlatform,
                                         String sendId,int sendIdentity){
        PushMessageRecord pushMessageRecord = new PushMessageRecord();
        pushMessageRecord.setSendResult(sendResult);
        pushMessageRecord.setCreateTime(new Date());
        pushMessageRecord.setJpushId(jpushId);
        pushMessageRecord.setMessageBody(messageBody);
        pushMessageRecord.setMessageType(messageType);
        pushMessageRecord.setSource(targetPlatform);
        pushMessageRecord.setSendId(sendId);
        pushMessageRecord.setSendIdentity(sendIdentity);

        pushMessageRecordDao.insert(pushMessageRecord);

    }

    private void saveBusinessMessage(ResponseResult responseResult, PushRequest pushRequest) {
        if (responseResult.getCode() == BusinessInterfaceStatus.SUCCESS.getCode()) {
            if (StringUtils.isNotBlank(pushRequest.getBusinessMessage())) {
                messageShowService.saveMessageShow(pushRequest.getTitle(), pushRequest.getBusinessMessage(), pushRequest.getAcceptId(), pushRequest.getAcceptIdentity(), pushRequest.getBusinessType());
            }

        }
    }

    private PushPayload buildMessagePushPayLoad(String title, String messageContent,String jpushId, String targetPlatform ,int audience ) {
        PushPayload.Builder builder = PushPayload.newBuilder();
        builder = setAudience(audience,builder,jpushId);

        builder.setPlatform(Platform.all());
        builder.setMessage(Message.newBuilder()
                .setTitle(title)
                .setMsgContent(messageContent)
                .build());

        PushPayload payload = builder.build();
        //保留多长时间消息
//        payload.resetOptionsTimeToLive(0L);

        return payload;
    }

    private PushPayload buildNoticePushPayLoad(String title, Map<String,String> messageContent,String jpushId, String targetPlatform ,int audience ){
        PushPayload.Builder builder = PushPayload.newBuilder();

        builder = setAudience(audience,builder,jpushId);

        builder.setPlatform(Platform.all());
        if(PlatformEnum.IOS.getValue().equalsIgnoreCase(targetPlatform)){
            builder.setNotification(Notification.newBuilder()
                    .addPlatformNotification(IosNotification.newBuilder()
                            .setContentAvailable(true)
                            .setAlert(title).addExtras(messageContent)
                            .build())
                    .build());
        }else if (PlatformEnum.ANDROID.getValue().equalsIgnoreCase(targetPlatform)){
            builder.setNotification(Notification.android(title,title, messageContent));
        }

        PushPayload payload = builder.build();
//        payload.resetOptionsTimeToLive(0L);

        return payload;
    }

    private PushPayload.Builder setAudience(int audience,PushPayload.Builder builder,String jpushId){
        if (AudienceEnum.ALIAS.getCode() == audience){
            builder.setAudience(Audience.alias(jpushId));
        }else if(AudienceEnum.REGISTRATION_ID.getCode() == audience){
            builder.setAudience(Audience.registrationId(jpushId));
        }else {
            return null;
        }
        return builder;
    }
}
