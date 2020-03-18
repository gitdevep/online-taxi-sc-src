package com.online.taxi.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.online.taxi.constant.AccountStatusCode;
import com.online.taxi.constatnt.IdentityEnum;
import com.online.taxi.dao.DriverInfoDao;
import com.online.taxi.dao.PassengerInfoDao;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.entity.DriverInfo;
import com.online.taxi.entity.PassengerInfo;
import com.online.taxi.request.PhoneRequest;
import com.online.taxi.service.IdRedisService;
import com.online.taxi.service.PhoneService;
import com.online.taxi.util.EncriptUtil;

import java.util.List;

/**
 * 司机服务
 *
 * @date 2018/08/15
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class PhoneServiceImpl implements PhoneService {

    @NonNull
    private DriverInfoDao driverInfoDao;

    @NonNull
    private PassengerInfoDao passengerInfoDao;

    @NonNull
    private  IdRedisService idRedisService;

    private static final Integer EXP_SECONDS = 120;
    /**
     * 通过id解密手机号
     * @param request id,idType
     * @return ResponseResult实例
     */
    @Override
    public ResponseResult getDecryptById(PhoneRequest request) {
        Integer idType = request.getIdType();
        if (null == idType) {
            log.error("idType为空！");
            return ResponseResult.fail(1, "idType为空！");
        }
        if (null == request.getInfoList()) {
            log.error("infoList为空！");
            return ResponseResult.fail(1, "infoList为空！");
        }
        if (ObjectUtils.nullSafeEquals(idType, IdentityEnum.PASSENGER.getCode())) {
            List<PassengerInfo> passengerInfoList = passengerInfoDao.selectByPrimaryKeyList();
            for (PassengerInfo list : passengerInfoList) {
                idRedisService.push(idType, list.getId(), list.getPhone(), EXP_SECONDS);
            }
        }
        if (ObjectUtils.nullSafeEquals(idType, IdentityEnum.DRIVER.getCode())) {
            List<DriverInfo> driverInfoList = driverInfoDao.selectByPrimaryKeyList();
            for (DriverInfo list : driverInfoList) {
                idRedisService.push(idType, list.getId(), list.getPhoneNumber(), EXP_SECONDS);
            }
        }
        for (int m = 0; m< request.getInfoList().size();m++){
            String strPhone = "" ;
            if(null != request.getInfoList().get(m)){
                if ( !StringUtils.isEmpty(idRedisService.pull(idType, request.getInfoList().get(m).getId()))) {
                    strPhone = EncriptUtil.decryptionPhoneNumber(idRedisService.pull(idType, request.getInfoList().get(m).getId()));
                }
            }
            request.getInfoList().get(m).setPhone(strPhone);
        }
        return ResponseResult.success(request);
    }
    /**
     * 加密手机号
     * @param request  手机号数组
     * @return ResponseResult实例
     */
    @Override
    public ResponseResult createEncrypt(PhoneRequest request) {
        if (null == request.getInfoList()) {
            log.error("手机号数组为空");
            return ResponseResult.fail(AccountStatusCode.PHONE_NUM_EMPTY.getCode(),AccountStatusCode.PHONE_NUM_EMPTY.getValue());
        }
        for (int i = 0; i < request.getInfoList().size(); i++) {
            String strPhone;
            if (null == request.getInfoList().get(i).getPhone()) {
                log.error("手机号为空");
                return ResponseResult.fail(AccountStatusCode.PHONE_NUM_EMPTY.getCode(),AccountStatusCode.PHONE_NUM_EMPTY.getValue());
            }
            strPhone = EncriptUtil.encryptionPhoneNumber(request.getInfoList().get(i).getPhone());
            if(StringUtils.isEmpty(strPhone)){
                request.getInfoList().get(i).setEncrypt(request.getInfoList().get(i).getPhone());
            }else {
                request.getInfoList().get(i).setEncrypt(strPhone);
            }
        }
        return ResponseResult.success(request);
    }

    /**
     * 通过密文解密手机号
     * @param request 密文数组
     * @return ResponseResult实例
     */
    @Override
    public ResponseResult getPhoneByEncryptList(PhoneRequest request) {

        if( null == request.getInfoList() ){
            log.error("密文数组为空");
            return ResponseResult.fail(AccountStatusCode.ENCRYPT_EMPTY.getCode(),AccountStatusCode.ENCRYPT_EMPTY.getValue());
        }
        for (int i = 0 ; i< request.getInfoList().size();i++){
            String strPhone ;
            if( null == request.getInfoList().get(i).getEncrypt()){
                log.error("密文为空");
                return ResponseResult.fail(AccountStatusCode.ENCRYPT_EMPTY.getCode(),AccountStatusCode.ENCRYPT_EMPTY.getValue());
            }
            strPhone =   EncriptUtil.decryptionPhoneNumber(request.getInfoList().get(i).getEncrypt());
            if(StringUtils.isEmpty(strPhone)){
                request.getInfoList().get(i).setPhone(request.getInfoList().get(i).getEncrypt());
            }else{
                request.getInfoList().get(i).setPhone(strPhone);
            }
        }
        return ResponseResult.success(request);
    }
}
