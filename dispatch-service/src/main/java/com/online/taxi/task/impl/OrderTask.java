package com.online.taxi.task.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.online.taxi.constatnt.IdentityEnum;
import com.online.taxi.consts.Const;
import com.online.taxi.consts.MessageType;
import com.online.taxi.consts.OrderTypeEnum;
import com.online.taxi.data.DriverData;
import com.online.taxi.data.OrderDto;
import com.online.taxi.dto.map.Vehicle;
import com.online.taxi.dto.map.request.OrderRequest;
import com.online.taxi.dto.push.PushLoopBatchRequest;
import com.online.taxi.dto.push.PushRequest;
import com.online.taxi.entity.CarInfo;
import com.online.taxi.entity.DriverInfo;
import com.online.taxi.entity.Order;
import com.online.taxi.entity.OrderRulePrice;
import com.online.taxi.lock.RedisLock;
import com.online.taxi.service.DispatchService;
import com.online.taxi.task.ITask;
import com.online.taxi.task.TaskCondition;
import com.online.taxi.util.DateUtils;
import com.online.taxi.util.EncriptUtil;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 */
@Data
@Slf4j
public class OrderTask implements ITask {
    private int orderId;
    private long nextExecuteTime;
    private int type;
    private int round;
    private static final int PIRED = 20;
    private List<TaskCondition> taskConditions = new ArrayList<>();
    private int status;
    private List<Integer> usedDriverId = new ArrayList<>();

    @Autowired
    private DispatchService dispatchService;

    @Override
    public int getTaskId() {
        return orderId;
    }

    @Override
    public int getOrderType() {
        return type;
    }

    @Override
    public boolean sendOrder(Order order, OrderRulePrice orderRulePrice, TaskCondition taskCondition, int round) {
        return false;
    }

    /**
     * 下次执行时间
     *
     * @return
     */
    @Override
    public boolean isTime() {
        return System.currentTimeMillis() > nextExecuteTime;
    }

    @Override
    public int execute(long current) {
        if (current < nextExecuteTime) {
            return status;
        }
        Order order = DispatchService.ins().getOrderById(orderId);
        OrderRulePrice orderRulePrice = DispatchService.ins().getOrderRulePrice(orderId);
        if (order == null || orderRulePrice == null) {
            status = STATUS_END;
            return status;
        }
        //判断是否已有司机接单
        if (order.getStatus() != Const.ORDER_STATUS_RE_RESERVED && order.getStatus() != Const.ORDER_STATUS_ORDER_START) {
            status = STATUS_END;
            return status;
        }
        if (round > taskConditions.size() - 1) {
            status = STATUS_END;
            //推送乘客,没人接单,或者加成功
            log.info("#orderId= " + orderId + "  round = " + round + "派单结束");
            taskEnd(order, orderRulePrice);
            return status;
        }
        TaskCondition taskCondition = taskConditions.get(round);
        log.info("#orderId = " + order.getId() + "  round = " + round + "  派单 round = " + round);

        boolean b = true;
        if (type == OrderTypeEnum.FORCE.getCode()) {
            forceSendOrder(order, orderRulePrice, taskCondition, round);
        } else if (type == OrderTypeEnum.SPECIAL.getCode()) {
            b = specialSendOrder(order, orderRulePrice, taskCondition, round);
        } else if (type == OrderTypeEnum.NORMAL.getCode()) {
            b = specialSendOrder(order, orderRulePrice, taskCondition, round);
        }
        // log.info("派单,第 = " + round + "轮 nextTime = " + taskCondition.getNextTime());
        round++;
        nextExecuteTime = current + TimeUnit.SECONDS.toMillis(taskCondition.getNextTime());
        if (!b) {
            nextExecuteTime = 0;
            log.info("#orderId= " + orderId + "  直接下一轮");
            return execute(current);
        }
        return status;
    }

    @Override
    public void taskEnd(Order order, OrderRulePrice orderRulePrice) {
        if (type == OrderTypeEnum.NORMAL.getCode()) {
            if (DispatchService.ins().hasDriver(orderRulePrice.getCityCode(), order.getOrderStartTime(), orderRulePrice.getCarLevelId(), order.getServiceType())) {
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
    }

    public void forceSendOrder(Order order, OrderRulePrice orderRulePrice, TaskCondition taskCondition, int round) {
        String orderKey = Const.REDIS_KEY_ORDER + order.getId();
        try {
            RedisLock.ins().lock(orderKey);
            Order newOrder = DispatchService.ins().getOrderById(order.getId());
            if (newOrder.getStatus() != Const.ORDER_STATUS_ORDER_START) {
                return;
            }
            for (Integer distance : taskCondition.getDistanceList()) {
                List<DriverData> list = DispatchService.ins().getCarByOrder(order, taskCondition, distance, usedDriverId, round, true);

                if (list == null) {
                    status = STATUS_END;
                    return;
                }
                log.info("#orderId= " + orderId + "  round = " + round + "  司机数量 = " + list.size());
                for (DriverData data : list) {
                    log.info("#orderId= " + orderId + "  round = " + round + "司机信息：" + JSONObject.fromObject(data));
                    Date startTime = new Date(order.getOrderStartTime().getTime() - TimeUnit.MINUTES.toMillis(taskCondition.getFreeTimeBefor()));
                    Date endTime = new Date(order.getOrderStartTime().getTime() + TimeUnit.MINUTES.toMillis(taskCondition.getFreeTimeAfter()));
                    String redisKey = Const.REDIS_KEY_DRIVER + data.getDriverInfo().getId();
                    DriverInfo driverInfo = data.getDriverInfo();
                    Vehicle amapVehicle = data.getAmapVehicle();
                    log.info("#orderId= " + orderId + "  round = " + round + "车辆高德信息：" + JSONObject.fromObject(amapVehicle));
                    try {
                        RedisLock.ins().lock(redisKey);
                        int count = DispatchService.ins().countDriverOrder(data.getDriverInfo().getId(), startTime, endTime);
                        if (count > 0) {
                            continue;
                        }
                        String otherPhone = order.getPassengerPhone();
                        if (StringUtils.isNotEmpty(order.getOtherPhone())) {
                            otherPhone = order.getOtherPhone();
                        }
                        OrderDto updateOrder = new OrderDto();

                        //手机号绑定
                        //try {
                        //    BindAxbResponse bindAxbResponse = DispatchService.ins().bindAxb(EncriptUtil.decryptionPhoneNumber(data.getDriverInfo().getPhoneNumber()), EncriptUtil.decryptionPhoneNumber(otherPhone), order.getOrderStartTime().getTime() + TimeUnit.DAYS.toMillis(1));
                        //    if (bindAxbResponse != null) {
                        //        updateOrder.setMappingNumber(bindAxbResponse.getSecretBindDTO().getSecretNo());
                        //        updateOrder.setMappingId(bindAxbResponse.getSecretBindDTO().getSubsId());
                        //    }
                        //} catch (Exception e) {
                        //    e.printStackTrace();
                        //}

                        updateOrder.setOrderId(orderId);
                        updateOrder.setId(orderId);
                        updateOrder.setPlateNumber(data.getCarInfo().getPlateNumber());
                        updateOrder.setDriverId(data.getDriverInfo().getId());
                        updateOrder.setDriverPhone(data.getDriverInfo().getPhoneNumber());
                        updateOrder.setStatus(Const.ORDER_STATUS_DRIVER_ACCEPT);
                        updateOrder.setDriverStatus(Const.ORDER_DRIVER_STATUS_ACCEPT);
                        updateOrder.setCarId(Integer.parseInt(data.getAmapVehicle().getVehicleId()));
                        updateOrder.setIsFollowing(data.getIsFollowing());
                        updateOrder.setDriverGrabTime(new Date());

                        //派单
                        boolean success = DispatchService.ins().updateOrder(updateOrder);
                        if (success) {
                            //更新高德
                            OrderRequest orderRequest = new OrderRequest();
                            orderRequest.setOrderId(order.getId() + "");
                            orderRequest.setCustomerDeviceId(order.getDeviceCode());
                            orderRequest.setType(order.getServiceType());
                            orderRequest.setStatus(2);
                            orderRequest.setOrderCity(orderRulePrice.getCityCode());
                            orderRequest.setVehicleId(order.getCarId() + "");
                            DispatchService.ins().updateAmapOrder(orderRequest);
                            status = STATUS_END;
                            //推送
                            String timeDesc = DateUtils.getDayString(order.getOrderStartTime());
                            JSONObject msg = new JSONObject();
                            // String messagePhoneNum = order.getPassengerPhone();
                            // if (order.getOtherPhone() != null) {
                            //     messagePhoneNum = order.getOtherPhone();
                            // }
                            DecimalFormat df = new DecimalFormat("#0.00");
                            String passengerPhone = EncriptUtil.decryptionPhoneNumber(order.getOtherPhone() == null || order.getOtherPhone().isEmpty() ? order.getPassengerPhone() : order.getOtherPhone());
                            String driverPhone = EncriptUtil.decryptionPhoneNumber(data.getDriverInfo().getPhoneNumber());
                            String content = timeDesc + ",乘客尾号" + StringUtils.substring(passengerPhone, passengerPhone.length() - 4) + ",从" + order.getStartAddress() + "到" + order.getEndAddress() + "预计行程" + orderRulePrice.getTotalDistance() + "公里" + orderRulePrice.getTotalPrice() + "元,请您合理安排接送时间";
                            msg.put("content", content);
                            msg.put("messageType", 4004);
                            msg.put("orderId", order.getId());
                            double startAddressDistance = DispatchService.ins().calDistance(data.getAmapVehicle().getLongitude(), data.getAmapVehicle().getLatitude(), order.getStartLongitude(), order.getStartLatitude());
                            msg.put("startAddressDistance", df.format(startAddressDistance / 1000));
                            msg.put("totalPrice", df.format(orderRulePrice.getTotalPrice()));
                            msg.put("totalDistance", df.format(orderRulePrice.getTotalDistance()));
                            msg.put("startTime", timeDesc);
                            msg.put("startAddress", order.getStartAddress());
                            msg.put("endAddress", order.getEndAddress());
                            msg.put("isFollowing", data.getIsFollowing());
                            msg.put("serviceType", orderRulePrice.getServiceTypeId());
                            {
                                PushRequest pushRequest = new PushRequest();
                                pushRequest.setSendId(order.getPassengerInfoId() + "");
                                pushRequest.setSendIdentity(IdentityEnum.PASSENGER.getCode());
                                pushRequest.setAcceptIdentity(IdentityEnum.DRIVER.getCode());
                                pushRequest.setAcceptId(data.getDriverInfo().getId() + "");
                                pushRequest.setMessageType(MessageType.ORDER_SEND_ORDER);
                                pushRequest.setTitle("派单");
                                pushRequest.setMessageBody(msg.toString());
                                pushRequest.setBusinessMessage(content);
                                pushRequest.setBusinessType(Const.BUSINESS_MESSAGE_TYPE_ORDER);
                                log.info("#orderId= " + orderId + "  round = " + round + "  派单推送 pushRequest = " + pushRequest);
                                DispatchService.ins().pushMessage(pushRequest);

                            }

                            //向乘客推送消息
                            msg = new JSONObject();
                            msg.put("orderId", orderId);
                            String contentMsg = "";
                            try {
                                CarInfo carInfo = data.getCarInfo();

                                msg.put("plateNumber", carInfo.getPlateNumber());
                                msg.put("brand", data.getCarInfo().getFullName());
                                msg.put("color", data.getCarInfo().getColor());
                                String driverPhoneNumber = driverInfo.getPhoneNumber();
                                msg.put("driverName", driverInfo.getDriverName());
                                msg.put("driverPhoneNum", StringUtils.substring(driverPhone, driverPhone.length() - 4));
                                msg.put("driverHeadImg", driverInfo.getHeadImg());
                                msg.put("mappingNumber", EncriptUtil.decryptionPhoneNumber(driverPhoneNumber));
                                msg.put("avgGrade", DispatchService.ins().getDriverEvaluateByDriverId(data.getDriverInfo().getId()));
                                msg.put("carImg", carInfo.getCarImg());
                                msg.put("driverLng", amapVehicle.getLongitude());
                                msg.put("driverLat", amapVehicle.getLatitude());
                                msg.put("messageType", MessageType.PASSENGER_SEND_ORDER);
                                msg.put("userFeature", order.getUserFeature());
                                log.info("#orderId= " + orderId + "  round = " + round + "  派单推送 pushRequest = " + msg);
                                contentMsg = "尊敬的逸品出行用户,您" + DateUtils.formatDate(order.getOrderStartTime(), DateUtils.yyyyMMddHHmm) + "的订单已指派给" + StringUtils.substring(data.getDriverInfo().getDriverName(), 0, 1) + "师傅,车牌号:" + data.getCarInfo().getPlateNumber() + ",车身颜色:" + data.getCarInfo().getColor();
                            } catch (Exception e) {
                                log.info("强派向乘客推送消息,组装消息异常");
                            } finally {
                                PushRequest pushRequest = new PushRequest();
                                pushRequest.setSendId(order.getDriverId() + "");
                                pushRequest.setSendIdentity(IdentityEnum.DRIVER.getCode());
                                pushRequest.setAcceptIdentity(IdentityEnum.PASSENGER.getCode());
                                pushRequest.setAcceptId(order.getPassengerInfoId() + "");
                                pushRequest.setMessageType(MessageType.PASSENGER_SEND_ORDER);
                                pushRequest.setTitle("派单");
                                pushRequest.setMessageBody(msg.toString());
                                pushRequest.setBusinessMessage(contentMsg);
                                pushRequest.setBusinessType(Const.BUSINESS_MESSAGE_TYPE_ORDER);
                                DispatchService.ins().pushMessage(pushRequest);
                            }

                            //短信司机
                            String phone = EncriptUtil.decryptionPhoneNumber(data.getDriverInfo().getPhoneNumber());
                            Map<String, Object> smsMap = new HashMap<>();
                            smsMap.put("type", getTypeDesc(orderRulePrice.getServiceTypeId(), data.getIsFollowing(), 1));
                            smsMap.put("time", DateUtils.formatDate(order.getOrderStartTime(), DateUtils.yyyyMMddHHmm));
                            smsMap.put("phone", StringUtils.substring(passengerPhone, passengerPhone.length() - 4));
                            smsMap.put("start", order.getStartAddress());
                            smsMap.put("end", order.getEndAddress());
                            DispatchService.ins().sendSmsMessage(phone, Const.SMS_FORCE_DISPATCH_DRIVER, smsMap);
                            return;
                        }
                    } catch (Exception e) {
                        log.error("forceSendOrder", e);
                    } finally {
                        log.info("unlock key = " + redisKey);
                        RedisLock.ins().unlock(redisKey);
                    }
                }
            }
        } finally {
            RedisLock.ins().unlock(orderKey);
        }
    }

    protected String getTypeDesc(int serviceType, int isFollowing, int userFeature) {
        String s = "";
        if (isFollowing == 1) {

            s = "顺风单";
        } else {
            if (serviceType == OrderTypeEnum.NORMAL.getCode()) {
                if (userFeature == Const.USER_FEATURE_CHILDREN) {
                    s = "儿童用车系统派单";
                } else if (userFeature == Const.USER_FEATURE_WOMAN) {
                    s = "女性用车系统派单";
                } else {
                    s = "预约派单";
                }
            } else if (serviceType == OrderTypeEnum.FORCE.getCode()) {
                s = "实时派单";
            }
        }
        return s;

    }

    public boolean specialSendOrder(Order order, OrderRulePrice orderRulePrice, TaskCondition taskCondition, int round) {
        List<DriverData> list = DispatchService.ins().getCarByOrder(order, taskCondition, taskCondition.getDistance(), usedDriverId, round, true);
        if (list == null) {
            status = STATUS_END;
            return false;
        }
        if (list.size() == 0) {
            log.info("#orderId= " + orderId + "  round = " + round + "司机数量0, 直接下一轮");
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
        messageBody.put("userFeature", order.getUserFeature());
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

    private static final int STATUS_END = -1;

}
