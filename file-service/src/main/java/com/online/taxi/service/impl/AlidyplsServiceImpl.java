package com.online.taxi.service.impl;

import com.online.taxi.constatnt.BusinessInterfaceStatus;
import com.online.taxi.dto.CallRecordsRequestDto;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.entity.CallRecords;
import com.online.taxi.entity.SecretVoiceRecords;
import com.online.taxi.mapper.CallRecordsMapper;
import com.online.taxi.mapper.SecretVoiceRecordsMapper;
import com.online.taxi.service.AlidyplsService;
import com.online.taxi.utils.AliPhoneConfig;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述
 *
 * @date 2018/8/21
 */
@Service
public class AlidyplsServiceImpl implements AlidyplsService {
    @Autowired
    private SecretVoiceRecordsMapper secretVoiceRecordsMapper;

    @Autowired
    private CallRecordsMapper callRecordsMapper;

    @Autowired
    private AliPhoneConfig aliPhoneConfig;

    @Override
    public ResponseResult callRecords(CallRecordsRequestDto[] callRecordsRequestDto) throws Exception {
        CallRecordsRequestDto callRecordsRequest = callRecordsRequestDto[0];
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(StringUtils.isNotBlank(callRecordsRequest.getStart_time()) && StringUtils.isNotBlank(callRecordsRequest.getRelease_time())) {
            CallRecords callRecords = callRecordsMapper.selectByPrimaryKey(callRecordsRequest.getCall_id());
            if(null == callRecords){
                //插入通话记录
                Date d1 = format.parse(callRecordsRequest.getStart_time());
                Date d2 = format.parse(callRecordsRequest.getRelease_time());
                if(!DateUtils.isSameInstant(d1, d2)) {
                    CallRecords callRecord = new CallRecords();
                    callRecord.setId(callRecordsRequest.getId());
                    callRecord.setPoolKey(aliPhoneConfig.getPoolKey());
                    callRecord.setSubId(String.valueOf(callRecordsRequest.getSub_id()));
                    callRecord.setCallId(callRecordsRequest.getCall_id());
                    callRecord.setCallType(String.valueOf(callRecordsRequest.getCall_type()));
                    callRecord.setPhoneNo(callRecordsRequest.getPhone_no());
                    callRecord.setSecretNo(callRecordsRequest.getSecret_no());
                    callRecord.setPeerNo(callRecordsRequest.getPeer_no());
                    callRecord.setReleaseDir(String.valueOf(callRecordsRequest.getRelease_dir()));
                    callRecord.setReleaseCause(callRecordsRequest.getRelease_cause());
                    callRecord.setStartTime(format.parse(callRecordsRequest.getStart_time()));
                    callRecord.setReleaseTime(format.parse(callRecordsRequest.getRelease_time()));
                    if(StringUtils.isNotBlank(callRecordsRequest.getCall_time())) {
                        callRecord.setCallTime(format.parse(callRecordsRequest.getCall_time()));
                    }
                    if(StringUtils.isNotBlank(callRecordsRequest.getRing_time())) {
                        callRecord.setRingTime(format.parse(callRecordsRequest.getRing_time()));
                    }
                    callRecord.setCreateTime(new Date());
                    callRecordsMapper.insertSelective(callRecord);

                    //插入录音
                    SecretVoiceRecords secretVoiceRecord = secretVoiceRecordsMapper.selectByPrimaryKey(callRecord.getCallId());
                    if(null == secretVoiceRecord){
                        SecretVoiceRecords secretVoiceRecords = new SecretVoiceRecords();
                        secretVoiceRecords.setCallId(callRecord.getCallId());
                        secretVoiceRecords.setSubsId(callRecord.getSubId());
                        secretVoiceRecords.setCallTime(callRecord.getCallTime());
                        secretVoiceRecords.setCreateTime(new Date());
                        secretVoiceRecordsMapper.insertSelective(secretVoiceRecords);
                    }
                    return ResponseResult.success(null);
                }else {
                    return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "解析失败");
                }
            }
        }
        return ResponseResult.success(null);
    }
}
