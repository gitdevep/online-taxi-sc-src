package com.online.taxi.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.taxi.constant.AccountStatusCode;
import com.online.taxi.constatnt.BusinessInterfaceStatus;
import com.online.taxi.constatnt.IdentityEnum;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.entity.PassengerInfo;
import com.online.taxi.request.GetTokenRequest;
import com.online.taxi.request.PassengerInfoExtRequest;
import com.online.taxi.request.PassengerInfoRequest;
import com.online.taxi.service.PassengerAddressService;
import com.online.taxi.service.PassengerInfoService;
import com.online.taxi.service.PassengerRegistHandleService;

import javax.xml.ws.Response;
import java.util.regex.Pattern;

/**
 * 乘客控制层
 * @date 2018/08/10
 */
@RestController
@RequestMapping("/passenger")
@RequiredArgsConstructor
@Slf4j
public class PassengerController {

    @NonNull
    private PassengerRegistHandleService passengerRegistHandleService;

    @NonNull
    private PassengerInfoService passengerInfoService;

    @NonNull
    private PassengerAddressService passengerAddressService;

    private static final int NUM = 11;
    /**
     * 乘客登录
     * @param request GetTokenRequest对象
     * @return ResponseResult实例
     */
    @PostMapping(value = "/regist")
    public ResponseResult getVerificationCode(@RequestBody GetTokenRequest request) {
        try {
            String phoneNum = request.getPhoneNum();
            if (StringUtils.isBlank(phoneNum)) {
                return ResponseResult.fail(AccountStatusCode.PHONE_NUM_EMPTY.getCode(), AccountStatusCode.PHONE_NUM_EMPTY.getValue(), phoneNum);
            }
            if (NUM != phoneNum.length()) {
                return ResponseResult.fail(AccountStatusCode.PHONE_NUM_DIGIT.getCode(), AccountStatusCode.PHONE_NUM_DIGIT.getValue(), phoneNum);
            }
            if (!Pattern.compile(AccountStatusCode.PHONE_NUMBER_VERIFICATION.getValue()).matcher(phoneNum).matches()) {
                return ResponseResult.fail(AccountStatusCode.PHONE_NUM_ERROR.getCode(), AccountStatusCode.PHONE_NUM_ERROR.getValue(), phoneNum);
            }
            request.setIdentityStatus(IdentityEnum.PASSENGER.getCode());
            return passengerRegistHandleService.handle(request);
        } catch (Exception e) {
            log.error("操作异常",e);
            e.printStackTrace();
            return ResponseResult.fail(1, "操作异常", request.getPhoneNum());
        }

    }

    /**
     * 查询乘客详情
     * @param request GetTokenRequest对象
     * @return ResponseResult实例
     */
    @PostMapping(value = "/passengerInfo")
    public ResponseResult getPassengerInfo(@RequestBody GetTokenRequest request) {

        return ResponseResult.success(passengerInfoService.getPassengerInfoView(request));
    }

    /**
     * 修改乘客信息
     * @param request PassengerInfoRequest对象
     * @return ResponseResult实例
     */
    @PostMapping(value = "/updatePassengerInfo")
    public ResponseResult updatePassengerInfo(@RequestBody PassengerInfoRequest request) {
        ResponseResult responseResult;
        if (null != request.getId()) {
            if (null != request.getData()) {
                if (null != request.getData().getPassengerAddress()) {
                    request.getData().getPassengerAddress().setPassengerInfoId(request.getId());
                }
                if (null != request.getData().getPassengerInfo()) {
                    request.getData().getPassengerInfo().setId(request.getId());
                }
            }
        }
        if (null != request.getData()) {
            if (null != request.getData().getPassengerAddress()) {
                responseResult = passengerAddressService.updatePassengerAddress(request.getData().getPassengerAddress());
                if (BusinessInterfaceStatus.SUCCESS.getCode() != responseResult.getCode()) {
                    return responseResult;
                }
            }
            if (null != request.getData().getPassengerInfo()) {
                responseResult = passengerInfoService.updatePassengerInfo(request.getData().getPassengerInfo());
                if (BusinessInterfaceStatus.SUCCESS.getCode() != responseResult.getCode()) {
                    return responseResult;
                }
            }
        }
        log.info("修改乘客信息成功");
        return ResponseResult.success("");
    }

    /**
     * 修改乘客额外信息
     * @param request
     * @return
     */
    @PostMapping("/ext")
    public ResponseResult updatePassengerInfoExt(@RequestBody PassengerInfoExtRequest request){
        Integer id = request.getId();
        if (id == null){
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(),"乘客Id为空");
        }
        Integer isContact = request.getIsContact();
        Integer isShare = request.getIsShare();
        String sharingTime = request.getSharingTime();
        if(null == isContact && null == isShare && StringUtils.isBlank(sharingTime)){
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(),"乘客额外信息为空");
        }
        PassengerInfo passengerInfo = new PassengerInfo();
        passengerInfo.setId(id);

        if (null != isContact){
            passengerInfo.setIsContact(isContact);
        }
        if (null != isShare){
            passengerInfo.setIsShare(isShare);
        }
        if (StringUtils.isNotBlank(sharingTime)){
            passengerInfo.setSharingTime(sharingTime);
        }

        int row = passengerInfoService.updatePassengerInfoById(passengerInfo);
        if (row > 0 ){
            return ResponseResult.success("");
        }else {
            return ResponseResult.fail("无可更新的乘客额外信息");
        }

    }


}
