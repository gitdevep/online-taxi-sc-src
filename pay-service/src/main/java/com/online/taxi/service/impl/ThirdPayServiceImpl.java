package com.online.taxi.service.impl;

import com.online.taxi.dao.AlipayCallbackInfoDao;
import com.online.taxi.dao.WeixinpayCallbackInfoDao;
import com.online.taxi.entity.AlipayCallbackInfo;
import com.online.taxi.entity.ScanPayResData;
import com.online.taxi.entity.WeixinpayCallbackInfo;
import com.online.taxi.service.ThirdPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @date 2018/9/14
 */
@Service
public class ThirdPayServiceImpl implements ThirdPayService {

    @Autowired
    private AlipayCallbackInfoDao alipayCallbackInfoDao;

    @Autowired
    private WeixinpayCallbackInfoDao weixinpayCallbackInfoDao;

    @Override
    public void insertAlipay(Map<String, String> params) {
        String notifyTime = params.get("notify_time");
        String notifyType = params.get("notify_type");
        String notifyId = params.get("notify_id");
        String appId = params.get("app_id");
        String charset = params.get("charset");
        String version = params.get("version");
        String signType = params.get("sign_type");
        String sign = params.get("sign");
        String tradeNo = params.get("trade_no");
        String outTradeNo = params.get("out_trade_no");
        String buyerId = params.get("buyer_id");
        String buyerLogonId = params.get("buyer_logon_id");
        String sellerId = params.get("seller_id");
        String sellerEmail = params.get("seller_email");
        String tradeStatus = params.get("trade_status");
        String totalAmount = params.get("total_amount");
        String receiptAmount = params.get("receipt_amount");
        String invoiceAmount = params.get("invoice_amount");
        String buyerPayAmount = params.get("buyer_pay_amount");
        String pointAmount = params.get("point_amount");
        String subject = params.get("subject");
        String body = params.get("body");
        String gmtCreate = params.get("gmt_create");
        String gmtPayment = params.get("gmt_payment");
        String fund_bill_list = params.get("fund_bill_list");
        AlipayCallbackInfo alipayCallbackInfo = new AlipayCallbackInfo();
        alipayCallbackInfo.setNotifyTime(notifyTime);
        alipayCallbackInfo.setNotifyType(notifyType);
        alipayCallbackInfo.setNotifyId(notifyId);
        alipayCallbackInfo.setAppId(appId);
        alipayCallbackInfo.setCharset(charset);
        alipayCallbackInfo.setVersion(version);
        alipayCallbackInfo.setSignType(signType);
        // alipayCallbackInfo.setSign(sign);
        alipayCallbackInfo.setTradeNo(tradeNo);
        alipayCallbackInfo.setOutTradeNo(outTradeNo);
        alipayCallbackInfo.setBuyerId(buyerId);
        alipayCallbackInfo.setBuyerLogonId(buyerLogonId);
        alipayCallbackInfo.setSellerId(sellerId);
        alipayCallbackInfo.setSellerEmail(sellerEmail);
        alipayCallbackInfo.setTradeStatus(tradeStatus);
        alipayCallbackInfo.setTotalAmount(totalAmount);
        alipayCallbackInfo.setReceiptAmount(receiptAmount);
        alipayCallbackInfo.setInvoiceAmount(invoiceAmount);
        alipayCallbackInfo.setBuyerPayAmount(buyerPayAmount);
        alipayCallbackInfo.setPointAmount(pointAmount);
        alipayCallbackInfo.setSubject(subject);
        alipayCallbackInfo.setBody(body);
        alipayCallbackInfo.setGmtCreate(gmtCreate);
        alipayCallbackInfo.setGmtPayment(gmtPayment);
        alipayCallbackInfo.setFundBillList(fund_bill_list);
        alipayCallbackInfoDao.insertSelective(alipayCallbackInfo);
    }

    @Override
    public void insertWeixinpay(ScanPayResData scanPayResData) {

        WeixinpayCallbackInfo weixinpayCallbackInfo = new WeixinpayCallbackInfo();

        weixinpayCallbackInfo.setMchId(scanPayResData.getMchId());

        weixinpayCallbackInfo.setAttach(scanPayResData.getAttach());
        weixinpayCallbackInfo.setBankType(scanPayResData.getBankType());
        weixinpayCallbackInfo.setCreateTime(new Date());
        weixinpayCallbackInfo.setErrCode(scanPayResData.getErrCode());
        weixinpayCallbackInfo.setOpenid(scanPayResData.getOpenid());
        weixinpayCallbackInfo.setOutTradeNo(scanPayResData.getOutTradeNo());
        weixinpayCallbackInfo.setResultCode(scanPayResData.getResultCode());

        weixinpayCallbackInfo.setTotalFee(Integer.valueOf(scanPayResData.getTotalFee()));
        weixinpayCallbackInfo.setTransactionId(scanPayResData.getTransactionId());
        weixinpayCallbackInfoDao.insertSelective(weixinpayCallbackInfo);
    }
}
