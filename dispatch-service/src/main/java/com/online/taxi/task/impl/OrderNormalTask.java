package com.online.taxi.task.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import com.online.taxi.constatnt.IdentityEnum;
import com.online.taxi.consts.Const;
import com.online.taxi.consts.MessageType;
import com.online.taxi.consts.OrderTypeEnum;
import com.online.taxi.data.DriverData;
import com.online.taxi.data.OrderDto;
import com.online.taxi.dto.push.PushLoopBatchRequest;
import com.online.taxi.dto.push.PushRequest;
import com.online.taxi.entity.ChargeRule;
import com.online.taxi.entity.Order;
import com.online.taxi.entity.OrderRulePrice;
import com.online.taxi.service.DispatchService;
import com.online.taxi.task.TaskCondition;
import com.online.taxi.util.DateUtils;
import com.online.taxi.util.EncriptUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 2018/9/27
 */
@Data
@Slf4j
public class OrderNormalTask extends AbstractTask {
    public OrderNormalTask(int orderId, int type) {
        this.orderId = orderId;
        this.type = type;
    }

    @Override
    public boolean sendOrder(Order order, OrderRulePrice orderRulePrice, TaskCondition taskCondition, int round) {
        List<DriverData> list = DispatchService.ins().getCarByOrder(order, taskCondition, taskCondition.getDistance(), usedDriverId, round, true);
        if (list == null) {
            status = STATUS_END;
            return false;
        }
        if (list.size() == 0) {
            log.info("#orderId= " + orderId + "  round = " + round + "司机数量0， 直接下一轮");
            return false;
        }
        log.info("#orderId= " + orderId + "  round = " + round + "司机数量 = " + list.size() + "  司机ID：" + list.toString());
        //推送
        JSONObject messageBody = new JSONObject();
        messageBody.put("orderId", orderId);
        messageBody.put("startTime", order.getOrderStartTime().getTime());
        messageBody.put("startAddress", order.getStartAddress());
        messageBody.put("endAddress", order.getEndAddress());
        messageBody.put("forecastPrice", orderRulePrice.getTotalPrice());
        messageBody.put("forecastDistance", orderRulePrice.getTotalDistance());
        messageBody.put("serviceType", order.getServiceType());
        if (order.getServiceType() == OrderTypeEnum.CHARTERED_CAR_FULL.getCode()) {
            messageBody.put("forecastTime", 8);
        }
        if (order.getServiceType() == OrderTypeEnum.CHARTERED_CAR_HALF.getCode()) {
            messageBody.put("forecastTime", 4);
        }
        messageBody.put("tagList", getTagsJson(order.getUserFeature()));
        String timeDesc = DateUtils.formatDate(order.getOrderStartTime(), DateUtils.yyMMddHHmm);
        String passengerPhone = EncriptUtil.decryptionPhoneNumber(order.getOtherPhone() == null || order.getOtherPhone().isEmpty() ? order.getPassengerPhone() : order.getOtherPhone());
        String content = "";
        if (order.getServiceType() == OrderTypeEnum.CHARTERED_CAR_HALF.getCode() || order.getServiceType() == OrderTypeEnum.CHARTERED_CAR_FULL.getCode()) {
            content = "您收到一条" + getTypeDesc2(order) + "预约单是否抢单，" + timeDesc + "上车点" + order.getStartAddress();
        } else {
            content = "您收到一条" + getTypeDesc2(order) + "预约单是否抢单," + timeDesc + "乘客尾号" + StringUtils.substring(passengerPhone, passengerPhone.length() - 4) + ",从" + order.getStartAddress() + "到" + order.getEndAddress() + "的订单";
        }
        messageBody.put("content", content);
        messageBody.put("userFeature", order.getUserFeature());
        ChargeRule chargeRule = DispatchService.ins().getChargeRule(orderRulePrice.getCityCode(), orderRulePrice.getServiceTypeId(), orderRulePrice.getCarLevelId());
        messageBody.put("charterCarInfo", DispatchService.ins().getChargeRuleStr(chargeRule));
        messageBody.put("time", chargeRule == null ? 0 : chargeRule.getBaseMinutes() / 60);
        List<String> driverList = new ArrayList<>();
        List<String> carScreenList = new ArrayList<>();
        int count = 0;
        for (DriverData data : list) {
            log.info("#orderId= " + orderId + "  round = " + round + "司机信息：" + JSONObject.fromObject(data));
            usedDriverId.add(data.getDriverInfo().getId());
            driverList.add(data.getDriverInfo().getId() + "");
            carScreenList.add(data.getCarInfo().getLargeScreenDeviceCode());
            count++;
            if (count >= taskCondition.getDriverNum()) {
                break;
            }
        }
        Order newOrder = DispatchService.ins().getOrderById(orderId);
        if (newOrder != null) {
            if (newOrder.getStatus() != Const.ORDER_STATUS_ORDER_START) {
                return false;
            }
        }
        if (driverList.size() > 0) {
            PushLoopBatchRequest request1 = new PushLoopBatchRequest(IdentityEnum.DRIVER.getCode(), driverList, MessageType.DRIVER_RESERVED, messageBody.toString(), order.getPassengerInfoId() + "", Const.IDENTITY_PASSENGER);
            log.info("#orderId= " + orderId + "  round = " + round + "  sendOrder PushLoopBatchRequest = " + request1);
            DispatchService.ins().loopMessageBatch(request1);
        }
        if (carScreenList.size() > 0) {
            PushLoopBatchRequest request2 = new PushLoopBatchRequest(IdentityEnum.CAR_SCREEN.getCode(), carScreenList, MessageType.CAR_SCREEN_RESERVED, messageBody.toString(), order.getPassengerInfoId() + "", Const.IDENTITY_PASSENGER);
            DispatchService.ins().loopMessageBatch(request2);
        }
        return true;
    }

    @Override
    public void taskEnd(Order order, OrderRulePrice orderRulePrice) {
        if (type == OrderTypeEnum.NORMAL.getCode()) {
            if (DispatchService.ins().hasDriver2(orderRulePrice.getCityCode(), order.getOrderStartTime(), orderRulePrice.getCarLevelId(), order.getServiceType())) {
                OrderDto updateOrder = new OrderDto();
                updateOrder.setOrderId(order.getId());
                updateOrder.setId(order.getId());
                updateOrder.setIsFakeSuccess(1);
                DispatchService.ins().updateOrder(updateOrder);
                log.info("#orderId= " + "  round = " + round + orderId + "  假成功");

                PushRequest pushRequest = new PushRequest();
                pushRequest.setSendId(order.getPassengerInfoId() + "");
                pushRequest.setSendIdentity(IdentityEnum.PASSENGER.getCode());
                pushRequest.setAcceptIdentity(IdentityEnum.PASSENGER.getCode());
                pushRequest.setAcceptId(order.getPassengerInfoId() + "");
                pushRequest.setMessageType(MessageType.FAKE_SCUCCESS);
                pushRequest.setTitle("假成功");
                JSONObject msg = new JSONObject();
                msg.put("messageType", MessageType.FAKE_SCUCCESS);
                msg.put("orderId", order.getId());
                pushRequest.setMessageBody(msg.toString());
                pushRequest.setBusinessMessage(msg.toString());
                log.info("#orderId= " + orderId + "  round = " + round + "  假成功消息 pushRequest = " + pushRequest);
                DispatchService.ins().pushMessage(pushRequest);
            }
        }
        log.info("#orderId= " + orderId + "  OrderNormalTask 派单结束");
    }
}
