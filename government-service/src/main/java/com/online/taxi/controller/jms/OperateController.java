package com.online.taxi.controller.jms;

import com.online.taxi.constatnt.BusinessInterfaceStatus;
import com.online.taxi.controller.AbstractJmsController;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.government.SupervisionData;
import com.online.taxi.dto.operate.OperatePayDto;
import com.online.taxi.entity.DriverWorkTime;
import com.online.taxi.entity.OrderPoints;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 经营支付
 *
 * @date 2018/9/14
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class OperateController extends AbstractJmsController {

    /**
     * 司机经营上线
     *
     * @param id id
     * @return ResponseResult
     */
    @GetMapping("/operate/login/{id}")
    public ResponseResult operateLogin(@PathVariable String id) {
        try {
            triggerListener(DriverWorkTime.class, id, SupervisionData.OperationType.INSERT.getName());
            return ResponseResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), e.getMessage());
        }
    }

    /**
     * 司机经营下线
     *
     * @param id id
     * @return ResponseResult
     */
    @GetMapping("/operate/logout/{id}")
    public ResponseResult operateLogout(@PathVariable String id) {
        try {
            triggerListener(DriverWorkTime.class, id, SupervisionData.OperationType.UPDATE.getName());
            return ResponseResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), e.getMessage());
        }
    }

    /**
     * 经营支付
     *
     * @param id 订单ID
     * @return ResponseResult
     */
    @GetMapping("/operate/pay/{id}")
    public ResponseResult operatePay(@PathVariable String id) {
        try {
            triggerListener(OperatePayDto.class, id, SupervisionData.OperationType.UPDATE.getName());
            return ResponseResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), e.getMessage());
        }
    }

    @GetMapping("position/positionDriver/{id}")
    public void positionDriver(@PathVariable String id) {
        try {
            triggerListener(OrderPoints.class, id, SupervisionData.OperationType.INSERT.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
