package com.online.taxi.dto;

import com.online.taxi.util.RandomStringGenerator;
import com.online.taxi.util.Signature;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 */
@XStreamAlias("xml")
@Data
@Slf4j
public class ScanPayQueryReqData {

    @XStreamAlias("appid")
    private String appid ;

    @XStreamAlias("mch_id")
    private String mchId ;

    @XStreamAlias("transaction_id")
    private String transactionId = "" ;

    @XStreamAlias("out_trade_no")
    private String outTradeNo ;

    @XStreamAlias("nonce_str")
    private String nonceStr ;

    @XStreamAlias("sign")
    private String sign ;

    public ScanPayQueryReqData(String transactionID, String outTradeNo,String appId,String mchId,String key){

        //微信分配的公众号ID（开通公众号之后可以获取到）
        setAppid(appId);
        //微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
        setMchId(mchId);
        //transaction_id是微信系统为每一笔支付交易分配的订单号，通过这个订单号可以标识这笔交易，它由支付订单API支付成功时返回的数据里面获取到。
        if (StringUtils.isBlank(transactionID)){
            setTransactionId("");
        }else {
            setTransactionId(transactionID);
        }


        //商户系统自己生成的唯一的订单号
        setOutTradeNo(outTradeNo);

        //随机字符串，不长于32 位
        setNonceStr(RandomStringGenerator.getRandomStringByLength(32));

        //根据API给的签名规则进行签名
        String sign = Signature.getSign(toMap(),key);
        setSign(sign);

    }

    private Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
                    XStreamAlias xStreamAlias = field.getAnnotation(XStreamAlias.class);
                    if(null != xStreamAlias){
                        map.put(xStreamAlias.value(), obj);
                    }
                }
            } catch (IllegalArgumentException e) {
                log.info("根据微信支付API给的签名规则进行签名，非法参数异常", e);
            } catch (IllegalAccessException e) {
                log.info("根据微信支付API给的签名规则进行签名，非法访问异常", e);
            }
        }
        return map;
    }
}