package com.online.taxi.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.taxi.constant.AccountStatusCode;
import com.online.taxi.dto.CarBaseInfoView;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.entity.CarInfo;
import com.online.taxi.request.CarChangeRequest;
import com.online.taxi.request.CarInfoRequest;
import com.online.taxi.request.UpdateCarRequest;
import com.online.taxi.service.CarBaseService;
import com.online.taxi.service.CarInfoService;

/**
 * 车辆控制层
 *
 * @date 2018/08/09
 **/
@RestController
@RequestMapping("/car")
@Slf4j
@RequiredArgsConstructor
public class CarInfoController {

    @NonNull
    private CarBaseService carService;

    @NonNull
    private CarInfoService carInfoService;

    /**
     * 修改车辆
     * @param request CarChangeRequest
     * @return ResponseResult
     */
    @PostMapping(value = "/updateCar")
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult carUpdate(@RequestBody CarChangeRequest request) {
        CarBaseInfoView carBaseInfoView = request.getData();
        if (StringUtils.isEmpty(carBaseInfoView.getCarInfo().getCityCode())) {
            log.error("城市代码为空！");
            return ResponseResult.fail(AccountStatusCode.CITY_CODE_EMPTY.getCode(),AccountStatusCode.CITY_CODE_EMPTY.getValue());
        }
        if ( null == carBaseInfoView.getCarInfo().getCarLevelId() || 0 == carBaseInfoView.getCarInfo().getCarLevelId()) {
            log.error("车辆级别id为空！");
            return ResponseResult.fail(AccountStatusCode.CAR_LEVEL_EMPTY.getCode(),AccountStatusCode.CAR_LEVEL_EMPTY.getValue());
        }
        if (null == carBaseInfoView.getCarInfo().getCarTypeId() || 0 == carBaseInfoView.getCarInfo().getCarTypeId()) {
            log.error("车辆类型id为空！");
            return ResponseResult.fail(AccountStatusCode.CAR_TYPE_EMPTY.getCode(),AccountStatusCode.CAR_TYPE_EMPTY.getValue());
        }
        if (StringUtils.isEmpty(carBaseInfoView.getCarInfo().getPlateNumber())) {
            log.error("车牌号为空！");
            return ResponseResult.fail(AccountStatusCode.PLATE_NUMBER_EMPTY.getCode(),AccountStatusCode.PLATE_NUMBER_EMPTY.getValue());
        }
        if (StringUtils.isEmpty(carBaseInfoView.getCarBaseInfo().getVinNumber())) {
            log.error("vin为空！");
            return ResponseResult.fail(AccountStatusCode.VIM_NUMBER_EMPTY.getCode(),AccountStatusCode.VIM_NUMBER_EMPTY.getValue());
        }
        if (StringUtils.isEmpty(carBaseInfoView.getCarInfo().getLargeScreenDeviceBrand())) {
            log.error("大屏品牌为空！");
            return ResponseResult.fail(AccountStatusCode.LARGE_SCREEN_DEVICE_BRAND_EMPTY.getCode(),AccountStatusCode.LARGE_SCREEN_DEVICE_BRAND_EMPTY.getValue());
        }
        if (StringUtils.isEmpty(carBaseInfoView.getCarInfo().getLargeScreenDeviceCode())) {
            log.error("大屏编号为空！");
            return ResponseResult.fail(AccountStatusCode.LARGE_SCREEN_DEVICE_CODE_EMPTY.getCode(),AccountStatusCode.LARGE_SCREEN_DEVICE_CODE_EMPTY.getValue());
        }
        if (null == carBaseInfoView.getCarInfo().getUseStatus()) {
            log.error("车辆状态为空！");
            return ResponseResult.fail(AccountStatusCode.USE_STATUS_EMPTY.getCode(),AccountStatusCode.USE_STATUS_EMPTY.getValue());
        }
        int carBaseInfoCode = carService.updateCarBaseInfoView(request.getData().getCarBaseInfo());
        int carInfoCode = carInfoService.updateByPrimaryKeySelective(request.getData().getCarInfo());
        if (1 != carBaseInfoCode) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("修改CarBaseInfo失败");
            return ResponseResult.fail("修改CarBaseInfo失败");
        } else if (1 != carInfoCode) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("修改CarInfo失败");
            return ResponseResult.fail("修改CarInfo失败");
        } else {
            log.info("修改CarInfo成功");
            return ResponseResult.success("");
        }
    }

    /**
     * 车辆录入
     * @param request CarInfoRequest
     * @return ResponseResult
     */
    @PostMapping(value = "/car")
    public ResponseResult carAdd(@RequestBody CarInfoRequest request) {
        CarBaseInfoView carBaseInfoView = request.getData();
        if (StringUtils.isEmpty(carBaseInfoView.getCarInfo().getCityCode())) {
            log.error("城市代码为空！");
            return ResponseResult.fail(AccountStatusCode.CITY_CODE_EMPTY.getCode(),AccountStatusCode.CITY_CODE_EMPTY.getValue());
        }
        if (null == carBaseInfoView.getCarInfo().getCarLevelId() || 0 == carBaseInfoView.getCarInfo().getCarLevelId()) {
            log.error("车辆级别id为空！");
            return ResponseResult.fail(AccountStatusCode.CAR_LEVEL_EMPTY.getCode(),AccountStatusCode.CAR_LEVEL_EMPTY.getValue());
        }
        if (null == carBaseInfoView.getCarInfo().getCarTypeId() || 0 == carBaseInfoView.getCarInfo().getCarTypeId()) {
            log.error("车辆类型id为空！");
            return ResponseResult.fail(AccountStatusCode.CAR_TYPE_EMPTY.getCode(),AccountStatusCode.CAR_TYPE_EMPTY.getValue());
        }
        if (StringUtils.isEmpty(carBaseInfoView.getCarInfo().getPlateNumber())) {
            log.error("车牌号为空！");
            return ResponseResult.fail(AccountStatusCode.PLATE_NUMBER_EMPTY.getCode(),AccountStatusCode.PLATE_NUMBER_EMPTY.getValue());
        }
        if (StringUtils.isEmpty(carBaseInfoView.getCarBaseInfo().getVinNumber())) {
            log.error("vin为空！");
            return ResponseResult.fail(AccountStatusCode.VIM_NUMBER_EMPTY.getCode(),AccountStatusCode.VIM_NUMBER_EMPTY.getValue());
        }
        if (StringUtils.isEmpty(carBaseInfoView.getCarInfo().getLargeScreenDeviceBrand())) {
            log.error("大屏品牌为空！");
            return ResponseResult.fail(AccountStatusCode.LARGE_SCREEN_DEVICE_BRAND_EMPTY.getCode(),AccountStatusCode.LARGE_SCREEN_DEVICE_BRAND_EMPTY.getValue());
        }
        if (StringUtils.isEmpty(carBaseInfoView.getCarInfo().getLargeScreenDeviceCode())) {
            log.error("大屏编号为空！");
            return ResponseResult.fail(AccountStatusCode.LARGE_SCREEN_DEVICE_CODE_EMPTY.getCode(),AccountStatusCode.LARGE_SCREEN_DEVICE_CODE_EMPTY.getValue());
        }
        if (null == carBaseInfoView.getCarInfo().getUseStatus()) {
            log.error("车辆状态为空！");
            return ResponseResult.fail(AccountStatusCode.USE_STATUS_EMPTY.getCode(),AccountStatusCode.USE_STATUS_EMPTY.getValue());
        }

        return carService.addCarBaseInfo(request.getData());
    }

    /**
     * 修改车辆
     * @param request 请求状态
     * @return ResponseResult实例
     */
    @PostMapping(value = "/updateCarInfo")
    public ResponseResult updateCarInfo(@RequestBody UpdateCarRequest request){
        CarInfo carInfo = new CarInfo();

        if(null != request.getId() ){
            carInfo.setId(request.getId());
        }else {
            log.error("id为空");
            return  ResponseResult.fail(AccountStatusCode.ID_EMPTY.getCode(),AccountStatusCode.ID_EMPTY.getValue());
        }
        if(null != request.getUseStatus()){
            carInfo.setUseStatus(request.getUseStatus());
        }
        if(null != request.getTotalMile()){
            carInfo.setTotalMile(request.getTotalMile());
        }
        int update = carInfoService.updateByPrimaryKeySelective(carInfo);
        if(0 == update){
            log.error("修改车辆状态失败");
            return ResponseResult.fail(1,"修改失败");
        }else {
            log.info("修改车辆状态成功");
            return ResponseResult.success("");
        }
    }

}
