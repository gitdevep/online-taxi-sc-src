package com.online.taxi.service.impl;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.online.taxi.constatnt.IdentityEnum;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.entity.PassengerInfo;
import com.online.taxi.entity.PassengerRegisterSource;
import com.online.taxi.request.GetTokenRequest;
import com.online.taxi.response.PassengerRegistResponse;
import com.online.taxi.service.AuthService;
import com.online.taxi.service.PassengerInfoService;
import com.online.taxi.service.PassengerRegistHandleService;
import com.online.taxi.util.EncriptUtil;
import com.online.taxi.util.JwtUtil;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * @date 2018/08/15
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PassengerRegistHandleServiceImpl implements PassengerRegistHandleService {

    @NonNull
    private PassengerInfoService passengerInfoService;

    @NonNull
    private AuthService authService;

    /**
     * 乘客登录
     * @param getTokenRequest 对象
     * @return ResponseResult实例
     */
    @Override
    public ResponseResult handle(GetTokenRequest getTokenRequest) {
        String phoneNumber = getTokenRequest.getPhoneNum();
        log.info("乘客手机号：" + phoneNumber);
        // 查询乘客信息
        String strPhoneNum = EncriptUtil.toHexString(EncriptUtil.encrypt(phoneNumber)).toUpperCase();
        log.info("加密后手机号：" + strPhoneNum);
        PassengerInfo passengerInfo = passengerInfoService.queryPassengerInfoByPhoneNum(strPhoneNum);
        log.info("根据手机号查询，乘客信息。" + passengerInfo);
        PassengerInfo passengerInfoTmp = new PassengerInfo();
        int passengerId;
        int isNewer = 0;
        if (null == passengerInfo) {
            isNewer = 1;
            // 若无乘客信息，记录新乘客信息
            Date date = new Date();
            passengerInfoTmp.setLastLoginTime(date);
            //登陆方式 1.验证码
            Byte method = 1;
            passengerInfoTmp.setLastLoginMethod(method);
            passengerInfoTmp.setPhone(strPhoneNum);
            passengerInfoTmp.setRegisterTime(date);
            passengerInfoTmp.setBalance(new BigDecimal(0));
            passengerInfoTmp.setCreateTime(date);
            log.info("新增乘客手机号：" + passengerInfoTmp.getPhone());
            passengerInfoService.insertPassengerInfo(passengerInfoTmp);

            passengerId = passengerInfoTmp.getId();
            //新增注册来源
            try {
                PassengerRegisterSource passengerRegisterSource = new PassengerRegisterSource();
                passengerRegisterSource.setPassengerInfoId(passengerId);
                String registerSource = getTokenRequest.getRegisterSource();
                passengerRegisterSource.setRegisterSource(registerSource);
                passengerRegisterSource.setMarketChannel(getTokenRequest.getMarketChannel());
                passengerRegisterSource.setCreateTime(new Date());
                passengerInfoService.insertPassengerRegisterSource(passengerRegisterSource);
            } catch (Exception e) {
                e.printStackTrace();
            }

            log.info("乘客注册或登录 - " + phoneNumber + " - 校验注册状态 - 用户未注册，已插入新用户记录");
            //初始化乘客钱包
            passengerInfoService.initPassengerWallet(passengerId);
        } else {
            log.info("乘客注册或登录 - " + phoneNumber + " - 校验注册状态 - 用户已注册");
            //若乘客登录或者注册过了，更新登录时间
            passengerId = passengerInfo.getId();
            passengerInfoService.updatePassengerInfoLoginTime(passengerId);
        }

        //乘客登录 生成jwtStr
        String subject = IdentityEnum.PASSENGER.getCode() + "_" + phoneNumber + "_" + passengerId;
        log.info("token:" + subject);
        String jwtStr = authService.createToken(subject);
        log.info("乘客注册或登录用户-" + phoneNumber + "- access_token:" + jwtStr);
        //多终端互踢

        passengerInfo = passengerInfoService.queryPassengerInfoByPhoneNum(strPhoneNum);

        return createResponse(jwtStr, passengerInfo.getPassengerName(),
                passengerInfo.getGender(), passengerInfo.getBalance(), phoneNumber,
                passengerInfo.getHeadImg(), passengerId,passengerInfo.getLastLoginTime(),
                passengerInfo.getLastLoginMethod(),passengerInfo.getIsContact(),
                passengerInfo.getIsShare(),passengerInfo.getSharingTime(),passengerInfo.getBirthday()==null?null:passengerInfo.getBirthday().getTime(),
                isNewer);
    }

    /**
     * 封装响应协议
     * @param accessToken TOKEN
     * @param passengerName 乘客姓名
     * @param sex 性别
     * @param balance 账户
     * @param phoneNum 手机号
     * @param headImg 头像
     * @param id id
     * @param lastLoginTime 登录时间
     * @param method 登录方式
     * @return ResponseResult实例
     */
    private ResponseResult createResponse(String accessToken, String passengerName, Byte sex, BigDecimal balance, String phoneNum, String headImg,
                                          Integer id, Date lastLoginTime, Byte method,Integer isContact,Integer isShare,String sharingTime,Long birthday,
                                          Integer isNewer) {
        PassengerRegistResponse response = new PassengerRegistResponse();
        response.setStatus("0");
        response.setAccessToken(accessToken);
        response.setPassengerName(StringUtils.isBlank(passengerName) ? "" : passengerName);
        response.setGender(sex == null ? 0 : sex);
        response.setId(id);
        response.setBalance(balance);
        response.setPhoneNum(phoneNum);
        response.setHeadImg(StringUtils.isBlank(headImg) ? "" : headImg);
        //极光ID
        String jPushId = IdentityEnum.PASSENGER.getCode() + "_" + phoneNum + "_" + Calendar.getInstance().getTimeInMillis();
        response.setJpushId(jPushId);
        response.setLastLoginTime(lastLoginTime);
        response.setLastLoginMethod(method);
        response.setIsContact(isContact);
        response.setIsShare(isShare);
        response.setSharingTime(sharingTime);
        response.setBirthday(birthday);
        response.setIsNewer(isNewer);
        return ResponseResult.success(response);
    }

    /**
     * 登出
     * @param request token
     * @return ResponseResult 实例
     */
    @Override
    public ResponseResult checkOut(GetTokenRequest request){
        String strToken = request.getToken();
        Claims claims = JwtUtil.parseJWT(strToken);
        String subject = claims.getSubject();
        authService.deleteToken(subject);
        return ResponseResult.success("");
    }
}
