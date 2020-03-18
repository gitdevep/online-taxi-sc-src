package com.online.taxi.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import com.online.taxi.constant.AccountStatusCode;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.entity.DriverInfo;

/**
 * 司机状态校验器
 * @date 2018/08/15
 **/
@Slf4j
public class DriverInfoValidator {
    public static ResponseResult hasError(String phoneNumber, DriverInfo driverInfo) {
        if (StringUtils.isBlank(phoneNumber)) {
            log.info("司机注册或登录 - " + phoneNumber + " - 手机号码为空");
            return ResponseResult.fail(Integer.valueOf(AccountStatusCode.PHONE_NUM_EMPTY.getCode()),"手机号码为空",phoneNumber);
        }

        if (null == driverInfo) {
            // 若无司机信息，返回
            log.info("司机注册或登录 - " + phoneNumber + " - 校验注册状态 - 司机未注册");
            return ResponseResult.fail(Integer.valueOf(AccountStatusCode.DRIVER_EMPTY.getCode()),AccountStatusCode.DRIVER_EMPTY.getValue(),phoneNumber);
        } else {
            log.info("司机注册或登录 - " + phoneNumber + " - 校验注册状态 - 用户已注册");
        }

        // 是否为启用状态
        Integer signStatus = driverInfo.getSignStatus();
        if (null == signStatus) {
            return ResponseResult.fail(Integer.valueOf(AccountStatusCode.DRIVER_UN_SIGN.getCode()),AccountStatusCode.DRIVER_UN_SIGN.getValue(),driverInfo.getId().toString());
        } else if (signStatus.intValue() == 0) {
            return ResponseResult.fail(Integer.valueOf(AccountStatusCode.DRIVER_UN_SIGN.getCode()),AccountStatusCode.DRIVER_UN_SIGN.getValue(),driverInfo.getId().toString());
        }

        // 是否为签约状态
        Integer useStatus = driverInfo.getUseStatus();
        if (null == useStatus) {
            return ResponseResult.fail(Integer.valueOf(AccountStatusCode.DRIVER_UN_USE.getCode()),AccountStatusCode.DRIVER_UN_USE.getValue(),driverInfo.getId().toString());
        } else if (useStatus.intValue() == 0) {
            return ResponseResult.fail(Integer.valueOf(AccountStatusCode.DRIVER_UN_USE.getCode()),AccountStatusCode.DRIVER_UN_USE.getValue(),driverInfo.getId().toString());
        }

        return null;
    }
}
