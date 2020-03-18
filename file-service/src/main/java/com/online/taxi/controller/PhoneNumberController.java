package com.online.taxi.controller;

//import com.aliyuncs.dyplsapi.model.v20170525.BindAxbResponse;
import com.aliyuncs.exceptions.ClientException;
import com.online.taxi.constatnt.BusinessInterfaceStatus;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.phone.BoundPhoneDto;
import com.online.taxi.dto.phone.request.PhoneNumberRequest;
import com.online.taxi.service.SecretPhoneNumberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 手机号控制
 * @date 2018/8/21
 */
@RestController
@RequestMapping("/phone")
@Slf4j
public class PhoneNumberController {

    private static final String IS_OK = "OK";

    @Autowired
    private SecretPhoneNumberService secretPhoneNumberService;

    /**
     * 手机号绑定
     * @param request
     * @return
     */
    @GetMapping(value="/bind")
    public ResponseResult PhoneNumberBind(PhoneNumberRequest request){
    	// 此处代码正确，由于缺少jar包，所以注释掉
//    	BindAxbResponse axbResponse = null;
//        try {
//            if(StringUtils.isNotBlank(request.getDriverPhone()) && StringUtils.isNotBlank(request.getPassengerPhone())){
//                log.info("driverPhone=="+request.getDriverPhone());
//                log.info("passengerPhone=="+request.getPassengerPhone());
//                log.info("失效时间=="+request.getExpiration());
//                axbResponse = secretPhoneNumberService.bindAxb(request);
//            }else{
//                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "绑定手机号为空");
//            }
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
//        if (axbResponse.getCode() != null && IS_OK.equals(axbResponse.getCode())) {
//            String axbSubsId = axbResponse.getSecretBindDTO() == null ? null : axbResponse.getSecretBindDTO().getSubsId();
//            String axbSecretNo = axbResponse.getSecretBindDTO() == null ? null : axbResponse.getSecretBindDTO().getSecretNo();
//            BoundPhoneDto boundPhoneDto = new BoundPhoneDto();
//            boundPhoneDto.setAxbSubsId(axbSubsId);
//            boundPhoneDto.setAxbSecretNo(axbSecretNo);
//            return ResponseResult.success(boundPhoneDto);
//        }else{
//            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "绑定手机号失败");
//        }
    	return null;
    }

    /**
     * 手机解绑
     * @param request
     * @return
     */
    @GetMapping("/unbind")
    public ResponseResult PhoneNumberUnbind(PhoneNumberRequest request){
    	// 此处代码正确，由于缺少jar包，所以注释掉
//    	try {
//        	
//            secretPhoneNumberService.unbind(request);
//            return ResponseResult.success("解绑成功");
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
        return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "解绑失败");
    }

}
