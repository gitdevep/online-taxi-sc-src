package com.online.taxi.interceptor;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 验证token
 * @date 2018/08/10
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    public static final String AUTHORIZATION = "Authorization";

    @NonNull
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {

        String authorization = request.getHeader(AUTHORIZATION);
        try {
            log.info("authorization:"+authorization);
            String subject = authService.checkToken(authorization);
            String[] subjectArr = subject.split("_");

            String identity = subjectArr[0];
            String phoneNum = subjectArr[1];
            String queryCarStatus = "/queryCarStatus";
            if (request.getServletPath().equals(queryCarStatus)) {
                phoneNum = (phoneNum == null ? "" : phoneNum);
            }
            if (phoneNum != null) {

                MDC.clear();
                MDC.put("identity", identity);
                MDC.put("phoneNum", phoneNum);
                // 如果token验证成功，将token对应的用户id存在request中，便于之后注入
                log.info("token有效:" + authorization);
                request.setAttribute("CURRENT_USER_ID", phoneNum);
                // 快过期了，续约
                return true;
            } else {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                log.warn("请求方法：" + handlerMethod.getMethod() + "token验证失败 :" + authorization + " ip :" + request.getRemoteAddr());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return  false;
            }
        } catch (Exception e) {

            log.warn("解析jwt失败 :" + authorization + " ip :" + request.getRemoteAddr());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            //response返回值
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            try {
                ResponseResult responseResult = new ResponseResult();
                responseResult.setCode(401);
                responseResult.setMessage("认证失败");
                out.append(JSONObject.fromObject(responseResult).toString());
                log.debug("返回信息:"+JSONObject.fromObject(responseResult).toString());
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            return false;
        }
    }
}
