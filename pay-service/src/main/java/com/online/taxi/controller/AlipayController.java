package com.online.taxi.controller;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.request.PayRequest;
import com.online.taxi.request.PayResultRequest;
import com.online.taxi.service.AlipayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 */
@RestController
@RequestMapping("/alipay")
@Slf4j
public class AlipayController {

    @Autowired
    private AlipayService alipayService;

    @RequestMapping(value = "/pretreatment" , method = RequestMethod.POST)
    public ResponseResult pretreatment(@RequestBody PayRequest payRequest){
        Integer yid = payRequest.getYid();
        Double capital = payRequest.getCapital();
        Double giveFee = payRequest.getGiveFee();
        String source = payRequest.getSource();
        Integer rechargeType = payRequest.getRechargeType();
        Integer orderId = payRequest.getOrderId();
        String data = alipayService.prePay(yid,capital,giveFee,source,rechargeType,orderId);
        return ResponseResult.success(data);
    }

    /**
     * 支付宝支付回调
     * 第一步： 在通知返回参数列表中，除去sign、sign_type两个参数外，凡是通知返回回来的参数皆是待验签的参数。
     * 第二步：将剩下参数进行url_decode, 然后进行字典排序，组成字符串，得到待签名字符串
     * 第三步：将签名参数（sign）使用base64解码为字节码串。
     * 第四步：使用RSA的验签方法，通过签名字符串、签名参数（经过base64解码）及支付宝公钥验证签名。
     * 第五步：在步骤四验证签名正确后，必须再严格按照如下描述校验通知数据的正确性。
     * 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
     * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
     * 3、校验通知中的seller_id（或者seller_email)
     * 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
     * 4、验证app_id是否为该商户本身。上述1、2、3、4有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。
     * 在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。
     * 在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
     * PS:（针对异步通知）程序执行完后必须打印输出“success”（不包含引号）。如果商户反馈给支付宝的字符不是success这7个字符，
     * 支付宝服务器会不断重发通知，直到超过24小时22分钟。
     * 一般情况下，25小时以内完成8次通知（通知的间隔频率一般是：4m,10m,10m,1h,2h,6h,15h）；
     *
     */
    @RequestMapping(value = "/callback")
    public void callback(HttpServletRequest request, HttpServletResponse response) {
        String returnStr = "failure";

        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }

            params.put(name, valueStr);
        }
        log.info("支付宝回调：" + params.toString());
        // 调用SDK验证签名
        boolean flag = alipayService.checkAlipaySign(params);
        // 验签成功
        if (flag) {
            boolean localflag = false;
            try {
                // 处理支付成功逻辑
                localflag = alipayService.callback(params);
                if (localflag) {
                    returnStr = "success";
                    log.info("支付成功----【订单号：" + params.get("out_trade_no") + "】");
                } else {
                    log.info("警告：支付宝--【支付回调入库失败，等待支付宝重新发起回调，订单号：" + params.get("out_trade_no") + "】");
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("警告：支付宝--【回调入库结果:失败！订单号：" + params.get("out_trade_no") + "】", e);
            }
        }
        try {
            PrintWriter out = response.getWriter();
            out.println(returnStr);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("支付宝回调返回错误", e);
        }
    }

    @GetMapping("/payResult")
    public ResponseResult payResult(PayResultRequest payResultRequest){
        return alipayService.payResult(payResultRequest.getOrderId(),payResultRequest.getOutTradeNo());
    }
}
