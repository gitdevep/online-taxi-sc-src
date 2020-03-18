package com.online.taxi.controller;

import com.online.taxi.constant.PayConst;
import com.online.taxi.constant.ResponseStatusEnum;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.request.ConsumeRequest;
import com.online.taxi.request.FreezeRequest;
import com.online.taxi.service.ConsumeService;
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
@RequestMapping("/consume")
public class ComsumeController {

    @Autowired
    private RefundService refundService;

    @Autowired
    private ConsumeService consumeService;

    /**
     * 冻结
     * @param freezeRequest
     * @return
     */
    @PostMapping("/freeze")
    public ResponseResult freeze(@RequestBody FreezeRequest freezeRequest){

        Integer yid = freezeRequest.getYid();
        Integer orderId = freezeRequest.getOrderId();
        Double price = freezeRequest.getPrice();
        Double limitPrice = freezeRequest.getLimitPrice();
        if(price == null || price.compareTo(PayConst.ZERO) <= 0){
            return ResponseResult.fail(ResponseStatusEnum.PRICE_EMPTY.getCode(),ResponseStatusEnum.PRICE_EMPTY.getValue());
        }

        return consumeService.freeze(yid,orderId,price,limitPrice);

    }

    /**
     * 解冻
     * @param freezeRequest
     * @return
     */
    @PostMapping("/unFreeze")
    public ResponseResult unFreeze(@RequestBody FreezeRequest freezeRequest){

        Integer yid = freezeRequest.getYid();
        Integer orderId = freezeRequest.getOrderId();
        Double price = freezeRequest.getPrice();

        return consumeService.unFreeze(yid,orderId);
    }

    /**
     * 扣款
     * @param consumeRequest
     * @return
     */
    @PostMapping("/pay")
    public ResponseResult pay(@RequestBody ConsumeRequest consumeRequest){

        Integer yid = consumeRequest.getYid();
        Integer orderId = consumeRequest.getOrderId();
        Double price = consumeRequest.getPrice();
        Double tailPrice = consumeRequest.getTailPrice();
        Double replenishPrice = consumeRequest.getReplenishPrice();
        if (yid == null){
            return ResponseResult.fail("yid为空");
        }
        if (orderId == null){
            return ResponseResult.fail("orderId为空");
        }

        if (price == null || price.compareTo(PayConst.ZERO) < 0) {
            if (tailPrice == null || tailPrice.compareTo(PayConst.ZERO) <= 0) {
                if (replenishPrice == null || replenishPrice.compareTo(PayConst.ZERO) <= 0) {
                    return ResponseResult.fail("价格不正确");
                }

            }
        }else if(price.compareTo(PayConst.ZERO) == 0){
            return consumeService.unFreeze(yid,orderId);

        }else {
            if (tailPrice != null && tailPrice.compareTo(PayConst.ZERO) > 0){
                return ResponseResult.fail("价格不正确");
            }
            if (replenishPrice != null && replenishPrice.compareTo(PayConst.ZERO) > 0){
                return ResponseResult.fail("价格不正确");
            }
        }
        return consumeService.pay(yid,orderId,price,tailPrice,replenishPrice);
    }
}
