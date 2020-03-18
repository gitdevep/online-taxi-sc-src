package com.online.taxi.service.impl;

import com.online.taxi.constant.*;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.entity.PassengerWalletRecord;
import com.online.taxi.service.CommonPayService;
import com.online.taxi.service.PassengerWalletService;
import com.online.taxi.service.RechargeService;
import com.online.taxi.util.BigDecimalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @date 2018/10/22
 */
@Service
public class RechargeServiceImpl implements RechargeService {

    @Autowired
    private PassengerWalletService passengerWalletService;

    @Autowired
    private CommonPayService commonPayService;

    @Override
    public ResponseResult bossRecharge(Integer yid, Double capital, Double giveFee,String description,String createUser) {

        description = commonPayService.createDescription(capital,giveFee,description);
        PassengerWalletRecord passengerWalletRecord = passengerWalletService.createWalletRecord(yid,capital,giveFee,
                PayTypeEnum.SYSTEM.getCode(), TradeTypeEnum.RECHARGE.getCode(),description,
                null, PayEnum.UN_PAID.getCode(),createUser);

        passengerWalletService.handleCallBack(RechargeTypeEnum.CHARGE.getCode(),passengerWalletRecord.getId(),"");
        return ResponseResult.success("");
    }
}
