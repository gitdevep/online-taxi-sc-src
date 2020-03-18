package com.online.taxi.service.impl;

import com.online.taxi.constant.*;
import com.online.taxi.constatnt.BusinessInterfaceStatus;
import com.online.taxi.dao.PassengerWalletDao;
import com.online.taxi.dao.PassengerWalletFreezeRecordDao;
import com.online.taxi.dto.RemainPrice;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.entity.PassengerWallet;
import com.online.taxi.entity.PassengerWalletFreezeRecord;
import com.online.taxi.entity.PassengerWalletRecord;
import com.online.taxi.service.CommonPayService;
import com.online.taxi.service.ConsumeService;
import com.online.taxi.service.PassengerWalletService;
import com.online.taxi.util.BigDecimalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @date 2018/8/22
 */
@Slf4j
@Service
public class ConsumeServiceImpl implements ConsumeService {

    @Autowired
    private PassengerWalletFreezeRecordDao passengerWalletFreezeRecordDao;

    @Autowired
    private PassengerWalletDao passengerWalletDao;

    @Autowired
    private PassengerWalletService passengerWalletService;

    @Autowired
    private CommonPayService commonPayService;

    private String lockKey = "lock_freeze_record_id:";

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult freeze(Integer yid, Integer orderId, Double price,Double limitPrice) {
        Date nowTime = new Date();
        PassengerWalletFreezeRecord passengerWalletFreezeRecord = passengerWalletFreezeRecordDao.selectByOrderIdAndYid(orderId,yid);
        if (null == passengerWalletFreezeRecord){
            Double capital = 0d;
            Double giveFee = 0d;
            PassengerWallet passengerWallet = passengerWalletDao.selectByPassengerInfoId(yid);

            if (null == passengerWallet){
                passengerWallet = passengerWalletService.initPassengerWallet(yid,0d,0d);
            } else{
                capital = passengerWallet.getCapital();
                giveFee = passengerWallet.getGiveFee();
            }
            //剩余可冻结金额
            Double remainPrice = BigDecimalUtil.add(capital.toString(),giveFee.toString());

            if(price.compareTo(remainPrice) <= 0){
                Double freezeCapital = 0d;
                Double freezeGiveFee = 0d;
                String description = "";
                if (price.compareTo(capital) <= 0){
                    freezeCapital = price;
                    description = "订单冻结（本金）";
                }else {
                    freezeCapital = capital;
                    freezeGiveFee = BigDecimalUtil.sub(price.toString(),capital.toString());
                    description = "订单冻结（本金+赠额）";
                }
                capital = BigDecimalUtil.sub(capital.toString(),freezeCapital.toString());
                giveFee = BigDecimalUtil.sub(giveFee.toString(),freezeGiveFee.toString());
                passengerWallet.setGiveFee(giveFee);
                passengerWallet.setCapital(capital);

                passengerWalletFreezeRecord = new PassengerWalletFreezeRecord();
                passengerWalletFreezeRecord.setCreateTime(nowTime);
                passengerWalletFreezeRecord.setFreezeCapital(freezeCapital);
                passengerWalletFreezeRecord.setFreezeGiveFee(freezeGiveFee);
                passengerWalletFreezeRecord.setOrderId(orderId);
                passengerWalletFreezeRecord.setPassengerInfoId(yid);
                passengerWalletFreezeRecord.setFreezeStatus(FreezeStatusEnum.FREEZE.getCode());

                passengerWalletFreezeRecordDao.insertSelective(passengerWalletFreezeRecord);

                passengerWalletService.createWalletRecord(yid,freezeCapital,freezeGiveFee,
                        PayTypeEnum.SYSTEM.getCode(),TradeTypeEnum.FREEZE.getCode(),
                        description,orderId,PayEnum.PAID.getCode(),"");

                freezeGiveFee = BigDecimalUtil.add(freezeGiveFee.toString(),passengerWallet.getFreezeGiveFee().toString());
                freezeCapital = BigDecimalUtil.add(freezeCapital.toString(),passengerWallet.getFreezeCapital().toString());

                passengerWallet.setFreezeGiveFee(freezeGiveFee);
                passengerWallet.setFreezeCapital(freezeCapital);
                passengerWalletDao.updateByPrimaryKeySelective(passengerWallet);
                return ResponseResult.success(passengerWalletFreezeRecord.getId());
            }else{
                limitPrice = limitPrice==null?0d:limitPrice;
                //冻结金额与余额差值
                Double sub = BigDecimalUtil.sub(price.toString(),remainPrice.toString());
                if (sub.compareTo(limitPrice) <= 0){

                    Double freezeCapital = capital;
                    Double freezeGiveFee = giveFee;
                    String description = "";

                    if (freezeCapital.compareTo(PayConst.ZERO) > 0 || freezeGiveFee.compareTo(PayConst.ZERO) > 0){

                        if (capital.compareTo(PayConst.ZERO) > 0 && giveFee.compareTo(PayConst.ZERO) > 0){
                            description = "订单冻结（本金+赠额）";
                        }else if (capital.compareTo(PayConst.ZERO) > 0){
                            description = "订单冻结（本金)";
                        }else if (giveFee.compareTo(PayConst.ZERO) > 0){
                            description = "订单冻结（赠额)";
                        }
                        capital = BigDecimalUtil.sub(capital.toString(),freezeCapital.toString());
                        giveFee = BigDecimalUtil.sub(giveFee.toString(),freezeGiveFee.toString());
                        passengerWallet.setGiveFee(giveFee);
                        passengerWallet.setCapital(capital);

                        passengerWalletFreezeRecord = new PassengerWalletFreezeRecord();
                        passengerWalletFreezeRecord.setCreateTime(nowTime);
                        passengerWalletFreezeRecord.setFreezeCapital(freezeCapital);
                        passengerWalletFreezeRecord.setFreezeGiveFee(freezeGiveFee);
                        passengerWalletFreezeRecord.setOrderId(orderId);
                        passengerWalletFreezeRecord.setPassengerInfoId(yid);
                        passengerWalletFreezeRecord.setFreezeStatus(FreezeStatusEnum.FREEZE.getCode());

                        passengerWalletFreezeRecordDao.insertSelective(passengerWalletFreezeRecord);

                        passengerWalletService.createWalletRecord(yid,freezeCapital,freezeGiveFee,
                                PayTypeEnum.SYSTEM.getCode(),TradeTypeEnum.FREEZE.getCode(),
                                description,orderId,PayEnum.PAID.getCode(),"");

                        freezeGiveFee = BigDecimalUtil.add(freezeGiveFee.toString(),passengerWallet.getFreezeGiveFee().toString());
                        freezeCapital = BigDecimalUtil.add(freezeCapital.toString(),passengerWallet.getFreezeCapital().toString());

                        passengerWallet.setFreezeGiveFee(freezeGiveFee);
                        passengerWallet.setFreezeCapital(freezeCapital);
                        passengerWalletDao.updateByPrimaryKeySelective(passengerWallet);
                        return ResponseResult.success(passengerWalletFreezeRecord.getId());
                    }else {
                        //不够冻结
                        return ResponseResult.fail(ResponseStatusEnum.FREEZE_NOT_ENOUGH.getCode(),ResponseStatusEnum.FREEZE_NOT_ENOUGH.getValue(),"");
                    }

                } else {
                    //不够冻结
                    return ResponseResult.fail(ResponseStatusEnum.FREEZE_NOT_ENOUGH.getCode(),ResponseStatusEnum.FREEZE_NOT_ENOUGH.getValue(),"");
                }

            }

        }else {
            //已经冻结
            return ResponseResult.fail(ResponseStatusEnum.NOT_ALLOW_RE_FREEZE.getCode(),ResponseStatusEnum.NOT_ALLOW_RE_FREEZE.getValue(),"");
        }
    }

    @Override
    public ResponseResult unFreeze(Integer yid, Integer orderId) {
        //查询冻结记录
        PassengerWalletFreezeRecord passengerWalletFreezeRecord = passengerWalletFreezeRecordDao.selectByOrderIdAndYid(orderId,yid);
        Double freezeCapital ;
        Double freezeGiveFee ;
        if(null != passengerWalletFreezeRecord){

            int freezeRecordId = passengerWalletFreezeRecord.getId();
            String lock = (lockKey + freezeRecordId).intern();
            String uuid = UUID.randomUUID().toString();
            log.info(lock);
            BoundValueOperations<String, String> lockRedis = redisTemplate.boundValueOps(lock);
            Boolean lockBoolean = lockRedis.setIfAbsent(uuid);
            if (lockBoolean) {
                lockRedis.expire(1500L, TimeUnit.SECONDS);
            } else {
                log.info("解冻记录已被锁定");
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "解冻记录已被锁定");
            }

            freezeCapital = passengerWalletFreezeRecord.getFreezeCapital();
            freezeGiveFee = passengerWalletFreezeRecord.getFreezeGiveFee();
            //开始解冻
            int row = passengerWalletService.unfreezeWallet(yid,freezeCapital,freezeGiveFee);
            if (row == 1){
                passengerWalletFreezeRecord.setFreezeStatus(FreezeStatusEnum.UNFREEZE.getCode());
                passengerWalletFreezeRecordDao.updateByPrimaryKeySelective(passengerWalletFreezeRecord);

                String description = commonPayService.createDescription(freezeCapital,freezeGiveFee,"订单解冻");

                passengerWalletService.createWalletRecord(yid,freezeCapital,freezeGiveFee,
                        PayTypeEnum.SYSTEM.getCode(),TradeTypeEnum.UN_FREEZE.getCode(),
                        description,orderId,PayEnum.PAID.getCode(),"");

            }
        }
        return ResponseResult.success("");

    }


    private ResponseResult pay(Integer yid, Integer orderId, Double price,String description) {
        //解冻
        ResponseResult responseResult = unFreeze(yid,orderId);
        if(responseResult.getCode() != BusinessInterfaceStatus.SUCCESS.getCode()){
            return responseResult;
        }
        //查询用户钱包
        PassengerWallet passengerWallet = passengerWalletDao.selectByPassengerInfoId(yid);
        Double remainPrice = 0d;

        if (null == passengerWallet){
            return ResponseResult.fail(ResponseStatusEnum.PASSENGER_WALLET_EMPTY.getCode(),
                    ResponseStatusEnum.PASSENGER_WALLET_EMPTY.getValue(),
                    "");
        } else {

            Double walletCapital = passengerWallet.getCapital();
            Double walletGiveFee = passengerWallet.getGiveFee();
            Double sum = BigDecimalUtil.add(walletCapital.toString(),walletGiveFee.toString());

            Double walletCapitalNew = walletCapital;
            Double walletGiveFeeNew = walletGiveFee;
            if(price.compareTo(walletCapital) <= 0){
                walletCapitalNew = BigDecimalUtil.sub(walletCapitalNew.toString(),price.toString());
                description = description + "（本金）";
            }else if(price.compareTo(sum) <= 0){
                walletCapitalNew = 0d;
                Double temp = BigDecimalUtil.sub(price.toString(),walletCapital.toString());
                walletGiveFeeNew = BigDecimalUtil.sub(walletGiveFee.toString(),temp.toString());
                description = description + "（本金+赠额）";
            }else {
                walletCapitalNew = 0d;
                walletGiveFeeNew = 0d;
                description = description + "（本金+赠额）";

                remainPrice = BigDecimalUtil.sub(price.toString(),sum.toString());
            }

            Double subCapital = BigDecimalUtil.sub(walletCapital.toString(),walletCapitalNew.toString());
            Double subGiveFee = BigDecimalUtil.sub(walletGiveFee.toString(),walletGiveFeeNew.toString());
            passengerWalletService.alterPassengerWalletPrice(yid,subCapital,subGiveFee,ChangeStatusEnum.SUB.getCode());
            PassengerWalletRecord passengerWalletRecord = new PassengerWalletRecord();
            if (subCapital.compareTo(PayConst.ZERO) != 0 || subGiveFee.compareTo(PayConst.ZERO) != 0) {
                //生成扣款记录
                passengerWalletRecord = passengerWalletService.createWalletRecord(yid, subCapital, subGiveFee, PayTypeEnum.BALANCE.getCode(), TradeTypeEnum.CONSUME.getCode(), description, orderId, PayEnum.PAID.getCode(),"");
            }

            RemainPrice remainPriceBean = new RemainPrice();
            remainPriceBean.setRemainPrice(remainPrice);
            remainPriceBean.setId(passengerWalletRecord.getId());
            return ResponseResult.success(remainPriceBean);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult pay(Integer yid, Integer orderId, Double price,
                              Double tailPrice,Double replenishPrice ) {

        if (null != price && price.compareTo(PayConst.ZERO) > 0){
            return pay(yid,orderId,price,"订单支付");
        }else{
            if(null == tailPrice){
                tailPrice = 0d;
            }
            if (null == replenishPrice){
                replenishPrice = 0d;
            }
            Double payTotalPrice = BigDecimalUtil.add(tailPrice.toString(),replenishPrice.toString());

            //查询用户钱包
            PassengerWallet passengerWallet = passengerWalletDao.selectByPassengerInfoId(yid);

            if (null == passengerWallet){
                return ResponseResult.fail(ResponseStatusEnum.PASSENGER_WALLET_EMPTY.getCode(),
                        ResponseStatusEnum.PASSENGER_WALLET_EMPTY.getValue(),
                        "");
            } else {
                Double walletCapital = passengerWallet.getCapital();
                Double walletGiveFee = passengerWallet.getGiveFee();
                Double sum = BigDecimalUtil.add(walletCapital.toString(),walletGiveFee.toString());
                if (payTotalPrice.compareTo(sum) > PayConst.ZERO){
                    return ResponseResult.fail(PayEnum.BALANCE_NOT_ENOUGH.getCode(),PayEnum.BALANCE_NOT_ENOUGH.getValue());
                }
            }

            if(null != tailPrice && tailPrice.compareTo(PayConst.ZERO) > 0){
                return pay(yid,orderId,tailPrice,"尾款支付");

            }
            if(null != replenishPrice && replenishPrice.compareTo(PayConst.ZERO) > 0){
                return pay(yid,orderId,replenishPrice,"订单补扣");
            }
            RemainPrice remainPrice = new RemainPrice();
            remainPrice.setRemainPrice(0d);
            return ResponseResult.success(remainPrice);
        }

    }
}
