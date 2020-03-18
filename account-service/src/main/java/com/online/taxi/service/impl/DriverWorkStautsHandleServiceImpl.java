package com.online.taxi.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.online.taxi.constant.AccountStatusCode;
import com.online.taxi.dao.DriverInfoDao;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.entity.DriverInfo;
import com.online.taxi.request.DriverWorkStatusRequest;
import com.online.taxi.service.DriverWorkStautsHandleService;

/**
 * @date 2018/08/15
 **/
@Service
@RequiredArgsConstructor
public class DriverWorkStautsHandleServiceImpl implements DriverWorkStautsHandleService {

    @NonNull
    private DriverInfoDao driverInfoDao;

    @NonNull
    private DriverInfoServiceImpl driverInfoService;

    /**
     * 修改司机工作状态
     * @param driverWorkStatusRequest 对象
     * @return ResponseResult实例
     */
    @Override
    public ResponseResult changeWorkStatus(DriverWorkStatusRequest driverWorkStatusRequest) {
        Integer id = driverWorkStatusRequest.getId();
        DriverInfo driverInfo = driverInfoDao.selectByPrimaryKey(id);
        if (null == driverInfo) {
            return ResponseResult.fail(AccountStatusCode.DRIVER_EMPTY.getCode(), AccountStatusCode.DRIVER_EMPTY.getValue());
        }
         //审核通过后，如果没有车辆，则提示
        Integer carId = driverInfo.getCarId();
        if (null == carId) {
            return ResponseResult.fail(AccountStatusCode.DRIVER_NO_CAR.getCode(), AccountStatusCode.DRIVER_NO_CAR.getValue());
        }

        if(null != driverWorkStatusRequest.getCsWorkStatus() ){
            Integer csWorkStatus = driverWorkStatusRequest.getCsWorkStatus();
            driverInfo.setCsWorkStatus(csWorkStatus);
        }
        if(null !=  driverWorkStatusRequest.getWorkStatus()){
            Integer workStatus = driverWorkStatusRequest.getWorkStatus();
            driverInfo.setWorkStatus(workStatus);
        }
        if(null != driverWorkStatusRequest.getIsFollowing()){
           Integer isFollowing =  driverWorkStatusRequest.getIsFollowing();
           driverInfo.setIsFollowing(isFollowing);
        }

         // 改变司机状态
        int update = driverInfoService.updateDriverInfoByPhoneNum(driverInfo);
        if (0 == update) {
            return ResponseResult.fail("修改司机状态失败!");
        }else{
            return ResponseResult.success("");
        }
    }
}
