package com.online.taxi.controller.jms;

import com.online.taxi.constatnt.BusinessInterfaceStatus;
import com.online.taxi.controller.AbstractJmsController;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.government.SupervisionData;
import com.online.taxi.entity.EvaluateDriver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 乘客评价控制器
 *
 * @date 2018/9/1
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class RatedController extends AbstractJmsController {

    private static final String ERR_EMPTY_ORDER_ID = "订单ID为空";

    /**
     * 乘客评价信息
     *
     * @param orderId 订单ID
     * @return ResponseResult
     */
    @GetMapping("/ratedPassenger/{orderId}")
    public ResponseResult ratedPassenger(@PathVariable String orderId) {
        return rated(orderId);
    }

    /**
     * 乘客投诉信息
     *
     * @param orderId 订单ID
     * @return ResponseResult
     */
    @GetMapping("/ratedPassengerComplaint/{orderId}")
    public ResponseResult ratedPassengerComplaint(@PathVariable String orderId) {
        return rated(orderId);
    }

    private ResponseResult rated(@PathVariable String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            log.error(ERR_EMPTY_ORDER_ID);
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), ERR_EMPTY_ORDER_ID);
        }

        try {
            triggerListener(EvaluateDriver.class, orderId, SupervisionData.OperationType.UPDATE.name());
            return ResponseResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("触发错误", e);
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), e.getMessage());
        }
    }
}
