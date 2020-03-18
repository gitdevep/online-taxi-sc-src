package com.online.taxi.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.taxi.constant.AccountStatusCode;
import com.online.taxi.constatnt.IdentityEnum;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.request.CheckTokenRequest;
import com.online.taxi.request.CreateTokenRequest;
import com.online.taxi.request.GetTokenRequest;
import com.online.taxi.service.AuthService;
import com.online.taxi.service.PassengerRegistHandleService;

/**
 * token检验
 *
 * @date 2018/08/10
 **/
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthTokenController {

    @NonNull
    private AuthService authService;

    @NonNull
    private PassengerRegistHandleService passengerRegistHandleService;

    /**
     * Token已失效
     */
    private static final String ONE = "1";

    /**
     * 鉴权
     * @param request TOKEN
     * @return ResponseResult实例
     */
    @PostMapping(value = "/checkToken")
    public ResponseResult checkToken(@RequestBody CheckTokenRequest request) {
        if (null == request.getToken()) {
            return ResponseResult.fail(1, AccountStatusCode.TOKEN_IS_EMPTY.getValue());
        }
        String code = authService.checkToken(request.getToken());

        if (ObjectUtils.nullSafeEquals(ONE, code)) {
            log.error("Token已失效");
            return ResponseResult.fail(1, "Token已失效");
        } else {
            log.info("成功");
            return ResponseResult.success("");
        }
    }

    /**
     * 生成token
     * @param request CreateTokenRequest对象
     * @return ResponseResult实例
     */
    @PostMapping(value = "/createToken")
    public ResponseResult createToken(@RequestBody CreateTokenRequest request) {

        //身份_电话号码_ID
        String subject = request.getType() + "_" + request.getPhoneNum() + "_" + request.getId();
        return ResponseResult.success(authService.createToken(subject));
    }

    /**
     * 登出
     * @param request 获取请求token的参数
     * @return ResponseResult实例
     * @throws Exception 异常
     */
    @PostMapping(value = "/checkOut")
    public ResponseResult checkOut(@RequestBody GetTokenRequest request) throws Exception {
        String strToken = request.getToken();

        //参数校验
        if (StringUtils.isEmpty(strToken)) {
            return ResponseResult.fail(AccountStatusCode.TOKEN_IS_EMPTY.getCode(), AccountStatusCode.TOKEN_IS_EMPTY.getValue());
        }else {
            return passengerRegistHandleService.checkOut(request);
        }




    }
}
