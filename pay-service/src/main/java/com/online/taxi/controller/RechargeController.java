package com.online.taxi.controller;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.request.PayRequest;
import com.online.taxi.service.RechargeService;
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
@RequestMapping("/recharge")
public class RechargeController {

    @Autowired
    private RefundService refundService;

    @Autowired
    private RechargeService rechargeService;

    /**
     * boss充值
     * @param payRequest
     * @return
     */
    @PostMapping("/boss")
    public ResponseResult refund(@RequestBody PayRequest payRequest){

        Integer yid = payRequest.getYid();
        Double capital = payRequest.getCapital()==null?0:payRequest.getCapital();
        Double giveFee = payRequest.getGiveFee()==null?0:payRequest.getGiveFee();
        String createUser = payRequest.getCreateUser();
        return rechargeService.bossRecharge(yid,capital,giveFee,"后台充值",createUser);
    }
}
