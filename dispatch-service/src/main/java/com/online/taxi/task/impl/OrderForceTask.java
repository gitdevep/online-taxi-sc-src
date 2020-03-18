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
import com.online.taxi.dto.map.request.OrderRequest;
import com.online.taxi.dto.phone.BoundPhoneDto;
import com.online.taxi.dto.push.PushRequest;
import com.online.taxi.entity.*;
import com.online.taxi.lock.RedisLock;
import com.online.taxi.service.DispatchService;
import com.online.taxi.task.TaskCondition;
import com.online.taxi.util.DateUtils;
import com.online.taxi.util.EncriptUtil;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @date 2018/9/27
 */
@Data
@Slf4j
public class OrderForceTask extends AbstractTask {
    public OrderForceTask(int orderId, int type) {
        this.orderId = orderId;
        this.type = type;
    }

    @Override
    public boolean sendOrder(Order order, OrderRulePrice orderRulePrice, TaskCondition taskCondition, int round) {
        String orderKey = Const.REDIS_KEY_ORDER + order.getId();
        try {
            RedisLock.ins().lock(orderKey);
            Order newOrder = DispatchService.ins().getOrderById(order.getId());
            if (newOrder.getStatus() != Const.ORDER_STATUS_ORDER_START) {
                return false;
            }
            for (Integer distance : taskCondition.getDistanceList()) {
                List<DriverData> list = DispatchService.ins().getCarByOrder(order, taskCondition, distance, usedDriverId, round, true);

                if (list == null) {
                    status = STATUS_END;
                    return false;
                }
                log.info("#orderId= " + orderId + "  round = " + round + "  司机数量 = " + list.size());
                for (DriverData data : list) {
                    log.info("#orderId= " + orderId + "  round = " + round + "司机信息：" + JSONObject.fromObject(data));
                    Date startTime = new Date(order.getOrderStartTime().getTime() - TimeUnit.MINUTES.toMillis(taskCondition.getFreeTimeBefor()));
                    Date endTime = new Date(order.getOrderStartTime().getTime() + TimeUnit.MINUTES.toMillis(taskCondition.getFreeTimeAfter()));
                    String redisKey = Const.REDIS_KEY_DRIVER + data.getDriverInfo().getId();
                    log.info("#orderId= " + orderId + "  round = " + round + "车辆高德信息：" + JSONObject.fromObject(data.getAmapVehicle()));
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
                        BoundPhoneDto bindAxbResponse1 = null;
                        BoundPhoneDto bindAxbResponse2 = null;
                        String otherMappingNumber = "";
                        String mappingNumber = "";
                        try {
                            if (StringUtils.isNotEmpty(order.getPassengerPhone())) {
                                bindAxbResponse1 = DispatchService.ins().bindAxb(EncriptUtil.decryptionPhoneNumber(data.getDriverInfo().getPhoneNumber()), EncriptUtil.decryptionPhoneNumber(order.getPassengerPhone()), order.getOrderStartTime().getTime() + TimeUnit.DAYS.toMillis(1));
                                if (bindAxbResponse1 != null) {
                                    updateOrder.setMappingNumber(bindAxbResponse1.getAxbSecretNo());
                                    updateOrder.setMappingId(bindAxbResponse1.getAxbSubsId());
                                    mappingNumber = bindAxbResponse1.getAxbSecretNo();
                                }
                            }
                            if (StringUtils.isNotEmpty(order.getOtherPhone())) {
                                bindAxbResponse2 = DispatchService.ins().bindAxb(EncriptUtil.decryptionPhoneNumber(data.getDriverInfo().getPhoneNumber()), EncriptUtil.decryptionPhoneNumber(order.getOtherPhone()), order.getOrderStartTime().getTime() + TimeUnit.DAYS.toMillis(1));
                                if (bindAxbResponse2 != null) {
                                    updateOrder.setOtherMappingNumber(bindAxbResponse2.getAxbSecretNo());
                                    updateOrder.setOtherMappingId(bindAxbResponse2.getAxbSubsId());
                                    otherMappingNumber = bindAxbResponse2.getAxbSecretNo();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

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
                        updateOrder.setUpdateType(Const.ORDER_STATUS_ORDER_START);
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
                            String driverPhone = EncriptUtil.decryptionPhoneNumber(data.getDriverInfo().getPhoneNumber());
                            String passengerPhone = EncriptUtil.decryptionPhoneNumber(order.getOtherPhone() == null || order.getOtherPhone().isEmpty() ? order.getPassengerPhone() : order.getOtherPhone());
                            pushDriver(order, orderRulePrice, data, passengerPhone);
                            pushPassanger(order, orderRulePrice, data, driverPhone);
                            //短信司机
                            smsDriver(order, orderRulePrice, data, passengerPhone, driverPhone);
                            pushOther(order, orderRulePrice, data, StringUtils.isEmpty(otherMappingNumber) ? driverPhone : otherMappingNumber);
                            pushPassenger(order, orderRulePrice, data, StringUtils.isEmpty(mappingNumber) ? driverPhone : mappingNumber);
                            return true;
                        } else {
                            log.info("#orderId = " + orderId + " 强派更新订单失败");
                            if (bindAxbResponse2 != null) {
                                DispatchService.ins().unbind(bindAxbResponse2.getAxbSubsId(), bindAxbResponse2.getAxbSecretNo());
                            }
                            if (bindAxbResponse1 != null) {
                                DispatchService.ins().unbind(bindAxbResponse1.getAxbSubsId(), bindAxbResponse1.getAxbSecretNo());
                            }
                        }
                    } catch (Exception e) {
                        log.error("forceSendOrder", e);
                    } finally {
                        log.info("unlock key = " + redisKey);
                        RedisLock.ins().unlock(redisKey);
                    }
                }
            }
            return true;
        } finally {
            RedisLock.ins().unlock(orderKey);
        }
    }

    public void smsDriver(Order order, OrderRulePrice orderRulePrice, DriverData data, String passengerPhone, String driverPhone) {
        try {
            /*Map<String, Object> smsMap = new HashMap<>();
            smsMap.put("type", getTypeDesc(orderRulePrice.getServiceTypeId(), data.getIsFollowing()));
            smsMap.put("time", DateUtils.formatDate(order.getOrderStartTime(), DateUtils.yyyyMMddHHmm));
            smsMap.put("phone", StringUtils.substring(passengerPhone, passengerPhone.length() - 4));
            smsMap.put("start", order.getStartAddress());
            smsMap.put("end", order.getEndAddress());
            DispatchService.ins().sendSmsMessage(driverPhone, Const.SMS_FORCE_DISPATCH_DRIVER, smsMap);*/
            smsDriverHx(order, orderRulePrice, data, passengerPhone, driverPhone);
        } catch (Exception e) {
            log.error("#orderId= " + orderId + " smsDriver error", e);
        }
    }

    public void smsDriverHx(Order order, OrderRulePrice orderRulePrice, DriverData data, String passengerPhone, String driverPhone) {
        if (order.getServiceType() == OrderTypeEnum.CHARTERED_CAR_FULL.getCode() || order.getServiceType() == OrderTypeEnum.CHARTERED_CAR_HALF.getCode()) {
            DispatchService.ins().sendSmsMessageHx(driverPhone, Const.HX_FORCE_DISPATCH_DRIVER_BAOCHE, getTypeDesc(orderRulePrice.getServiceTypeId(), data.getIsFollowing()), DateUtils.formatDate(order.getOrderStartTime(), DateUtils.yyMMddHHmm), order.getStartAddress(), StringUtils.substring(passengerPhone, passengerPhone.length() - 4));
        } else {
            DispatchService.ins().sendSmsMessageHx(driverPhone, Const.HX_FORCE_DISPATCH_DRIVER, getTypeDesc(orderRulePrice.getServiceTypeId(), data.getIsFollowing()), DateUtils.formatDate(order.getOrderStartTime(), DateUtils.yyMMddHHmm), StringUtils.substring(passengerPhone, passengerPhone.length() - 4), order.getStartAddress(), order.getEndAddress());
        }
    }

    public void pushOther(Order order, OrderRulePrice orderRulePrice, DriverData driverData, String driverPhone) {
        try {

            if (order.getOrderType() != 2) {
                return;
            }
            PassengerInfo passengerInfo = DispatchService.ins().getPassengerInfo(order.getPassengerInfoId());
            String timeDesc = DateUtils.formatDate(order.getOrderStartTime(), DateUtils.yyMMddHHmm);
            String pname = passengerInfo.getPassengerName();
            String color = driverData.getCarInfo().getColor();
            String plateNumber = driverData.getCarInfo().getPlateNumber();
            // String desc = timeDesc + "从" + order.getStartAddress() + "到" + order.getEndAddress();

            String bookTime = "预定时间";
            String phone = EncriptUtil.decryptionPhoneNumber(order.getOtherPhone());

            DispatchService.ins().sendSmsMessageHx(phone, Const.HX_FORCE_DISPATCH_PASSENGER, pname, color, plateNumber, StringUtils.substring(driverData.getDriverInfo().getDriverName(), 0, 1) + "师傅", driverPhone);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pushPassenger(Order order, OrderRulePrice orderRulePrice, DriverData driverData, String driverPhone) {
        try {
            // HX_FORCE_DISPATCH_PASSENGER2
            String name = StringUtils.substring(driverData.getDriverInfo().getDriverName(), 0, 1) + "师傅";
            String plateNumber = driverData.getCarInfo().getPlateNumber();
            // driverPhone
            String timeDesc = DateUtils.formatDate(order.getOrderStartTime(), DateUtils.yyMMddHHmm);
            String servicePhone = "0571-56030631";
            DispatchService.ins().sendSmsMessageHx(EncriptUtil.decryptionPhoneNumber(order.getPassengerPhone()), Const.HX_FORCE_DISPATCH_PASSENGER2, name, plateNumber, driverPhone, servicePhone);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pushPassanger(Order order, OrderRulePrice orderRulePrice, DriverData data, String driverPhone) {
        JSONObject msg = new JSONObject();
        msg.put("orderId", orderId);
        String contentMsg = "";
        try {
            CarInfo carInfo = data.getCarInfo();
            msg.put("plateNumber", carInfo.getPlateNumber());
            msg.put("brand", data.getCarInfo().getFullName());
            msg.put("color", data.getCarInfo().getColor());
            String driverPhoneNumber = data.getDriverInfo().getPhoneNumber();
            msg.put("driverName", data.getDriverInfo().getDriverName());
            msg.put("driverPhoneNum", StringUtils.substring(driverPhone, driverPhone.length() - 4));
            msg.put("driverHeadImg", data.getDriverInfo().getHeadImg());
            msg.put("mappingNumber", EncriptUtil.decryptionPhoneNumber(driverPhoneNumber));
            msg.put("avgGrade", DispatchService.ins().getDriverEvaluateByDriverId(data.getDriverInfo().getId()));
            msg.put("carImg", carInfo.getCarImg());
            msg.put("driverLng", data.getAmapVehicle().getLongitude());
            msg.put("driverLat", data.getAmapVehicle().getLatitude());
            msg.put("messageType", MessageType.PASSENGER_SEND_ORDER);
            log.info("#orderId= " + orderId + "  round = " + round + "  派单推送 pushRequest = " + msg);
            contentMsg = "尊敬的逸品出行用户,您" + DateUtils.formatDate(order.getOrderStartTime(), DateUtils.yyMMddHHmm) + "的订单已指派给" + StringUtils.substring(data.getDriverInfo().getDriverName(), 0, 1) + "师傅,车牌号:" + data.getCarInfo().getPlateNumber() + ",车身颜色:" + data.getCarInfo().getColor();
        } catch (Exception e) {
            log.info("强派向乘客推送消息,组装消息异常");
            e.printStackTrace();
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
    }

    public void pushDriver(Order order, OrderRulePrice orderRulePrice, DriverData data, String passengerPhone) {
        try {
            String timeDesc = DateUtils.formatDate(order.getOrderStartTime(), DateUtils.yyMMddHHmm);
            JSONObject msg = new JSONObject();
            DecimalFormat df = new DecimalFormat("#0.00");
            String typeDesc = "";
            String useFeature = order.getUserFeature();
            String content = "";
            if (order.getServiceType() == OrderTypeEnum.CHARTERED_CAR_HALF.getCode() || order.getServiceType() == OrderTypeEnum.CHARTERED_CAR_FULL.getCode()) {
                content = "您收到一条包车派单," + timeDesc + "上车点" + order.getStartAddress() + "乘客尾号" + StringUtils.substring(passengerPhone, passengerPhone.length() - 4) + ",请合理安排接乘时间";
            } else {
                content = "您收到一条" + getTypeDesc(orderRulePrice.getServiceTypeId(), data.getIsFollowing()) + "," + timeDesc + "乘客尾号" + StringUtils.substring(passengerPhone, passengerPhone.length() - 4) + ",从" + order.getStartAddress() + "到" + order.getEndAddress() + "的订单,请合理安排接乘时间";
            }
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
            msg.put("userFeature", order.getUserFeature());

            ChargeRule chargeRule = DispatchService.ins().getChargeRule(orderRulePrice.getCityCode(), orderRulePrice.getServiceTypeId(), orderRulePrice.getCarLevelId());
            msg.put("charterCarInfo", DispatchService.ins().getChargeRuleStr(chargeRule));
            msg.put("time", chargeRule == null ? 0 : chargeRule.getBaseMinutes() / 60);
            if (order.getServiceType() == OrderTypeEnum.CHARTERED_CAR_FULL.getCode()) {
                msg.put("forecastTime", 8);
            }
            if (order.getServiceType() == OrderTypeEnum.CHARTERED_CAR_HALF.getCode()) {
                msg.put("forecastTime", 4);
            }
            msg.put("tagList", getTagsJson(order.getUserFeature()));

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
        } catch (Exception e) {
            log.error("#orderId= " + orderId + " pushDriver error", e);
        }
    }

    @Override
    public void taskEnd(Order order, OrderRulePrice orderRulePrice) {
        log.info("#orderId= " + orderId + "  OrderForceTask  强派结束");
    }
}
