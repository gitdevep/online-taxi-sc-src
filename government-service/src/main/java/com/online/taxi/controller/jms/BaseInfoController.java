package com.online.taxi.controller.jms;

import com.online.taxi.constatnt.BusinessInterfaceStatus;
import com.online.taxi.controller.AbstractJmsController;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.baseinfo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2018/8/30
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class BaseInfoController extends AbstractJmsController {

    @GetMapping("/baseInfo/company/{id}")
    public ResponseResult company(@PathVariable String id, @RequestParam(value = "operation", defaultValue = "update") String operation) {
        try {
            triggerListener(BaseInfoCompanyDto.class, id, operation);
            return ResponseResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), e.getMessage());
        }
    }

    @GetMapping("/baseInfo/companyFare/{id}")
    public ResponseResult companyFare(@PathVariable String id, @RequestParam(value = "operation", defaultValue = "update") String operation) {
        try {
            triggerListener(BaseInfoCompanyFareDto.class, id, operation);
            return ResponseResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), e.getMessage());
        }
    }

    @GetMapping("/baseInfo/companyPay/{id}")
    public ResponseResult companyPay(@PathVariable String id, @RequestParam(value = "operation", defaultValue = "update") String operation) {
        try {
            triggerListener(BaseInfoCompanyPayDto.class, id, operation);
            return ResponseResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), e.getMessage());
        }
    }

    @GetMapping("/baseInfo/companyPermit/{id}")
    public ResponseResult companyPermit(@PathVariable String id, @RequestParam(value = "operation", defaultValue = "update") String operation) {
        try {
            triggerListener(BaseInfoCompanyPermitDto.class, id, operation);
            return ResponseResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), e.getMessage());
        }
    }

    @GetMapping("/baseInfo/companyService/{id}")
    public ResponseResult companyService(@PathVariable String id, @RequestParam(value = "operation", defaultValue = "update") String operation) {
        try {
            triggerListener(BaseInfoCompanyServiceDto.class, id, operation);
            return ResponseResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), e.getMessage());
        }
    }

    @GetMapping("/baseInfo/companyStat/{id}")
    public ResponseResult companyStat(@PathVariable String id, @RequestParam(value = "operation", defaultValue = "update") String operation) {
        try {
            triggerListener(BaseInfoCompanyStatDto.class, id, operation);
            return ResponseResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), e.getMessage());
        }
    }
}
