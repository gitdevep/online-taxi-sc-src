package com.online.taxi.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.online.taxi.constant.AccountStatusCode;
import com.online.taxi.dao.CarBaseInfoDao;
import com.online.taxi.dao.CarInfoDao;
import com.online.taxi.dto.CarBaseInfoView;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.entity.CarBaseInfo;
import com.online.taxi.service.CarBaseService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 车辆服务
 *
 * @date 2018/08/15
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class CarBaseServiceImpl implements CarBaseService{

    @NonNull
    private CarInfoDao carInfoDao;

    @NonNull
    private CarBaseInfoDao carBaseInfoDao;

    /**
     * 修改
     * @param carBaseInfo 对象
     * @return int
     */
    @Override
    public int updateCarBaseInfoView(CarBaseInfo carBaseInfo) {
        return carBaseInfoDao.updateByPrimaryKeySelective(carBaseInfo);
    }

    /**
     * 添加
     * @param carBaseInfoView 对象
     * @return ResponseResult实例
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addCarBaseInfo(CarBaseInfoView carBaseInfoView) {
        Map<String, Object> param = new HashMap<>(16);
        param.put("carPlateNumber", carBaseInfoView.getCarInfo().getPlateNumber());
        if (carInfoDao.countCarInfo(param) > 0) {
            return ResponseResult.fail(AccountStatusCode.DUPLICATE_PLATE_NUMBER.getCode(),AccountStatusCode.DUPLICATE_PLATE_NUMBER.getValue(), carBaseInfoView.getCarInfo().getPlateNumber());
        }

        carBaseInfoView.getCarInfo().setUpdateTime(new Date());
        carBaseInfoView.getCarBaseInfo().setRegisterTime(new Date());
        carBaseInfoView.getCarInfo().setCreateTime(new Date());
        int carInfoCode = carInfoDao.insertSelective(carBaseInfoView.getCarInfo());
        if (0 == carInfoCode) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseResult.fail("添加carInfo失败");
        }
        carBaseInfoView.getCarBaseInfo().setId(carBaseInfoView.getCarInfo().getId());
        carBaseInfoView.getCarBaseInfo().setCreateTime(new Date());
        int carBaseInfoCode = carBaseInfoDao.insertSelective(carBaseInfoView.getCarBaseInfo());
        if (0 == carBaseInfoCode) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseResult.fail("添加carBaseInfo失败");
        }
        return ResponseResult.success(carBaseInfoView);
    }
}
