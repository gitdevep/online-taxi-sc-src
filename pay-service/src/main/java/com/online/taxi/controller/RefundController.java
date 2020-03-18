package com.online.taxi.controller;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.request.RefundRequest;
import com.online.taxi.service.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2018/8/21
 */
@RestController
@RequestMapping("/refund")
public class RefundController {

    @Autowired
    private RefundService refundService;

    /**
     * 订单退款
     * @param refundRequest
     * @return
     */
    @PostMapping("/order")
    public ResponseResult refund(@RequestBody RefundRequest refundRequest){

        Integer yid = refundRequest.getYid();
        Integer orderId = refundRequest.getOrderId();
        Double refundPrice = refundRequest.getRefundPrice();
        String createUser = refundRequest.getCreateUser();
        return refundService.refund(yid,orderId,refundPrice,createUser);

    }
}
