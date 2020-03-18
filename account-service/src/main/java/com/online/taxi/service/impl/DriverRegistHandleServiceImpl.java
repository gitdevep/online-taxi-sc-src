package com.online.taxi.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.online.taxi.constatnt.IdentityEnum;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.entity.DriverInfo;
import com.online.taxi.request.GetTokenRequest;
import com.online.taxi.response.DriverRegistResponse;
import com.online.taxi.service.DriverRegistHandleService;
import com.online.taxi.util.DriverInfoValidator;
import com.online.taxi.util.EncriptUtil;

import java.util.Calendar;

/**
 * 司机端短信校验
 *
 * @date 2018/08/15
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class DriverRegistHandleServiceImpl implements DriverRegistHandleService {

    @NonNull
    private DriverInfoServiceImpl driverInfoService;

    @NonNull
    private DriverInfoCacheServiceImpl driverCacheService;

    @NonNull
    private AuthServiceImpl authService;

    /**
     * 登录
     * @param getTokenRequest 对象
     * @return ResponseResult实例
     */
    @Override
    public ResponseResult checkIn(GetTokenRequest getTokenRequest) {

        String phoneNumber = getTokenRequest.getPhoneNum();

        // 查询司机信息
        String strPhoneNum = EncriptUtil.toHexString(EncriptUtil.encrypt(phoneNumber)).toUpperCase();
        DriverInfo driverInfo = driverInfoService.queryDriverInfoByPhoneNum(strPhoneNum);
        ResponseResult errResponse = DriverInfoValidator.hasError(phoneNumber, driverInfo);
        if (null != errResponse) {
            return errResponse;
        }
        // 将司机信息更新至缓存
        driverCacheService.put(phoneNumber, JSONObject.fromObject(driverInfo).toString());

        // 司机登录 生成jwtStr
        String subject = IdentityEnum.DRIVER.getCode() + "_" + phoneNumber + "_" + driverInfo.getId();

        String jwtStr = authService.createToken(subject);
        log.info("司机注册或登录用户-" + phoneNumber + "- access_token:" + jwtStr);
        return createResponse(jwtStr, driverInfo.getDriverName(), driverInfo.getGender(), driverInfo.getWorkStatus(), driverInfo.getHeadImg(), driverInfo.getId(), phoneNumber);
    }

    /**
     * 创建返回实例
     * @param accessToken token
     * @param driverName 司机姓名
     * @param gender 性别
     * @param checkStatus  1已注册但未审核；2审核中；3审核通过；4审核未通过
     * @param headImg 头像
     * @param driverId 司机id
     * @param phoneNumber 手机号
     * @return ResponseResult实例
     */
    private ResponseResult createResponse(String accessToken, String driverName, Integer gender, Integer checkStatus, String headImg, Integer driverId, String phoneNumber) {
        DriverRegistResponse response = new DriverRegistResponse();
        response.setWorkStatus("0");
        response.setAccessToken(accessToken);
        response.setDriverName(StringUtils.isBlank(driverName) ? "" : driverName);
        response.setPhoneNumber(phoneNumber);
        response.setGerder(null == gender ? 1 : gender);
        response.setCheckStatus(checkStatus);
        response.setHeadImg(StringUtils.isBlank(headImg) ? "" : headImg);
        response.setDriverId(driverId);
        // 极光ID
        String jPushId = IdentityEnum.DRIVER.getCode() + "_" + phoneNumber + "_" + Calendar.getInstance().getTimeInMillis();
        response.setJpushId(jPushId);
        return ResponseResult.success(response);
    }
}
