package com.online.taxi.util;

import lombok.extern.slf4j.Slf4j;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 */
@Slf4j
public class Signature {

    public static String getSign(Map<String,Object> map,String key){
        ArrayList<String> list = new ArrayList<>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!="" &&entry.getValue()!=null){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        
        result += "key=" + key;
        log.info("签名前："+result);
        result = md5(result);
        log.info("签名后："+result);
        return result;
    }

    private static String md5(String str){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, md5.digest())).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            log.error("Signature.MD5 NoSuchAlgorithmException", e);
        } catch (UnsupportedEncodingException e) {
            log.error("Signature.MD5 UnsupportedEncodingException", e);
        }
        return null;
    }

    public static String getIOSSign(String appId,String timeStamp,String nonceStr,String prepayId,String partnerId,String key ){
        Map<String,Object> map = new HashMap<>(6);
        map.put("appid", appId);
        map.put("partnerid", partnerId);
        map.put("prepayid",prepayId);
        map.put("timestamp",timeStamp);
        map.put("noncestr",nonceStr);
        map.put("package","Sign=WXPay");
        return getSign(map,key);
    }

    /**
     * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
     * @param responseString API返回的XML数据字符串
     * @return API签名是否合法
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static boolean checkIsSignValidFromResponseString(String responseString,String key) throws ParserConfigurationException, IOException, SAXException {

        Map<String,Object> map = XstreamUtil.getMapFromXML(responseString);

        String signFromAPIResponse = map.get("sign").toString();
        if(signFromAPIResponse=="" || signFromAPIResponse == null){
            return false;
        }

        map.put("sign","");
        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        String signForAPIResponse = Signature.getSign(map,key);

        if(!signForAPIResponse.equals(signFromAPIResponse)){
            return false;
        }
        return true;
    }

}