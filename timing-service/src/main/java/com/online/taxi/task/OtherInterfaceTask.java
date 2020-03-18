package com.online.taxi.task;

import com.online.taxi.constatnt.BusinessInterfaceStatus;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.entity.Order;
import com.online.taxi.request.FreezeRequest;
import com.online.taxi.response.StsToken;
import com.online.taxi.util.RestTemplateHepler;
import com.online.taxi.utils.ServicesConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述
 *
 * @date 2018/10/30
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class OtherInterfaceTask {

    private String lockKey = "lock_order:";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ServicesConfig servicesConfig;

    public StsToken getALiToken(String token){
        StsToken stsToken = null;
        try {
            ResponseResult responseResult = restTemplate.getForObject(servicesConfig.getFile() + "/sts/authorization/" + token, ResponseResult.class);
            if (BusinessInterfaceStatus.SUCCESS.getCode() != responseResult.getCode()) {
                stsToken = null;
                return stsToken;
            }
            JSONObject jsonobject = JSONObject.fromObject(responseResult.getData());
            stsToken= (StsToken)JSONObject.toBean(jsonobject,StsToken.class);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("authorization:", e);
        }
            return stsToken;
    }

    /**
     * 钱包解冻
     * @param orderId
     * @param passengerInfoId
     * @return
     */
    public ResponseResult walletUnfreeze(Integer orderId,Integer passengerInfoId){
        String lock = (lockKey + orderId).intern();
        BoundValueOperations<String, String> lockRedis;
        String uuid = UUID.randomUUID().toString();
        lockRedis = redisTemplate.boundValueOps(lock);
        Boolean lockBoolean = lockRedis.setIfAbsent(uuid);
        if (lockBoolean) {
            lockRedis.expire(15L, TimeUnit.SECONDS);
        }else{
            redisTemplate.delete(lock);
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(),"解冻失败");
        }
        ResponseResult responseResult;
        FreezeRequest freezeRequest = new FreezeRequest();
        freezeRequest.setOrderId(orderId);
        freezeRequest.setYid(passengerInfoId);
        try{
            responseResult = restTemplate.postForObject(servicesConfig.getPay() + "/order", freezeRequest, ResponseResult.class);
            if (BusinessInterfaceStatus.SUCCESS.getCode() != responseResult.getCode()) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(),"解冻失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        redisTemplate.delete(lock);
        return ResponseResult.success("解冻成功");
    }
}
