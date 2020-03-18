package com.online.taxi.entity;

import com.online.taxi.util.RandomStringGenerator;
import com.online.taxi.util.Signature;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求被扫支付API需要提交的数据
 */
@XStreamAlias("xml")
@Data
public class WeixinXmlPayRequest {

    @XStreamAlias("appid")
    private String appid ;

    @XStreamAlias("mch_id")
    private String mchId ;

    @XStreamAlias("device_info")
    private String deviceInfo ;

    @XStreamAlias("nonce_str")
    private String nonceStr ;

    @XStreamAlias("sign")
    private String sign ;

    @XStreamAlias("body")
    private String body ;

    @XStreamAlias("attach")
    private String attach ;

    @XStreamAlias("out_trade_no")
    private String outTradeNo ;

    @XStreamAlias("total_fee")
    private int totalFee ;

    @XStreamAlias("spbill_create_ip")
    private String spbillCreateIp ;

    @XStreamAlias("time_start")
    private String timeStart;

    @XStreamAlias("time_expire")
    private String timeExpire ;

    @XStreamAlias("goods_tag")
    private String goodsTag ;

    @XStreamAlias("auth_code")
    private String authCode ;

    @XStreamAlias("trade_type")
    private String tradeType ="JSAPI";

    @XStreamAlias("notify_url")
    private String notifyUrl = "";

    @XStreamAlias("openid")
    private String openid;

    /**
     * @param body           要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
     * @param attach  order 普通订单  deposit 押金
     * @param outTradeNo     商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
     * @param totalFee       订单总金额，单位为“分”，只能整数
     * @param spBillCreateIP 订单生成的机器IP
     */
    public WeixinXmlPayRequest(String body,
                               String outTradeNo, int totalFee, String spBillCreateIP,
                               String notify_url, String trade_type, String openid, String attach,
                               String appId, String mchId, String key) {

    	setAppid(appId);
    	//微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
    	setMchId(mchId);
    	
        //要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
        setBody(body);

        //支付订单里面可以填的附加数据，API会将提交的这个附加数据原样返回，有助于商户自己可以注明该笔消费的具体内容，方便后续的运营和记录
        setAttach(attach);

        //商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
        setOutTradeNo(outTradeNo);

        //订单总金额，单位为“分”，只能整数
        setTotalFee(totalFee);

        setNotifyUrl(notify_url);
        //订单生成的机器IP
        setSpbillCreateIp(spBillCreateIP);
        setTradeType(trade_type);

        //随机字符串，不长于32 位
        setNonceStr(RandomStringGenerator.getRandomStringByLength(32));

        //根据API给的签名规则进行签名
        String sign = Signature.getSign(toMap(),key);
        setSign(sign);

    }


    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if (obj != null) {
                    XStreamAlias xStreamAlias = field.getAnnotation(XStreamAlias.class);
                    if(null != xStreamAlias){
                        map.put(xStreamAlias.value(), obj);
                    }

                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

}