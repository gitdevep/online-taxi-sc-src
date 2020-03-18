package com.online.taxi.constant;

import com.online.taxi.constatnt.CodeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *  账户状态码
 * @date 2018/08/15
 **/
@Getter
@AllArgsConstructor
public enum AccountStatusCode implements CodeEnum {
    /**
     * 校验2000-2099
     * */
    PHONE_NUM_EMPTY(2000, "手机号为空"),
    ENCRYPT_EMPTY(2001, "密文为空"),
    PHONE_NUM_DIGIT(2003,"手机号不为11位"),
    PHONE_NUM_ERROR(2004,"手机号不正确"),
    TOKEN_IS_EMPTY(2005,"Token为空!"),
    PHONE_NUM_REPEAT(2006,"手机号已添加过"),
    /**
     * 车机状态（3701-3799）
     * */
    DRIVER_UN_SIGN(3701,"司机未签约"),
    DRIVER_UN_USE(3702,"司机停用"),
    /**
     * 状态（20-30）
     * */
    ADD(1,"添加"),
    UPDATE(2,"修改"),
    UPDATE_FAIL(21,"修改失败"),
    SELECT_FAIL(22,"修改失败"),
    ADD_FAIL(23,"添加失败"),
    DELETE_FAIL(24,"删除失败"),
    /**
     * 凌动项目用的-(2000-2099)
     * */
    VEHICLE_REPEAT(2001,"车辆id重复"),
    DUPLICATE_PLATE_NUMBER(2002,"车牌号重复"),
    CITY_CODE_EMPTY(2003,"城市代码为空"),
    CAR_LEVEL_EMPTY(2004,"车辆级别id为空"),
    DRIVER_STATUS_EMPTY(2005, "司机工作状态为空"),
    CAR_TYPE_EMPTY(2006,"车辆类型id为空"),
    PLATE_NUMBER_EMPTY(2007,"车牌号为空"),
    VIM_NUMBER_EMPTY(2008,"vin为空"),
    LARGE_SCREEN_DEVICE_BRAND_EMPTY(2009,"大屏品牌为空"),
    LARGE_SCREEN_DEVICE_CODE_EMPTY(2010,"大屏编号为空"),
    USE_STATUS_EMPTY(2011,"状态为空"),
    DRIVER_LEADER_EMPTY(2012,"司机主管为空"),
    GENDER_EMPTY(2013,"性别为空"),
    DRIVER_NAME_EMPTY(2014,"司机姓名为空"),
    DRIVER_CARD_ID_EMPTY(2015,"司机身份证为空"),
    DRIVER_PHONE_NUM_EMPTY(2016,"司机手机号为空"),
    CONTRACT_END_DATE_EMPTY(2017,"合同协议有效期止为空"),
    CONTRACT_START_DATE_EMPTY(2018,"合同协议有效期始为空"),
    DRIVER_USE_STATUS(2019,"司机启用状态为空"),
    SPEED_EMPTY(2020,"速度为空"),
    LATITUDE_EMPTY(2021,"纬度为空"),
    LONGITUDE_EMPTY(2022,"经度为空"),
    ID_EMPTY(2023,"ID为空"),
    ADDRESS_EMPTY(2024,"地址为空"),
    DRIVER_EMPTY(2026, "此手机号码未添加为逸品司机"),
    PHONE_NUMBER_VERIFICATION(2027,"^((13[0-9])|(14[56789])|(15[0-9])|(16[124567])|(17[0-8])|(18[0-9])|(19[0-9])|(92[0-9])|(98[0-9]))\\d{8}$"),
    DRIVER_NO_CAR(2037, "司机没有车辆"),
    IDENTITY_ERROR(2038,"身份类型错误");
    private final int code;
    private final String value;

}
