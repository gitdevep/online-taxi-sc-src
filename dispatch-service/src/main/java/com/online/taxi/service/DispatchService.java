package com.online.taxi.service;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.online.taxi.config.Cache;
import com.online.taxi.constatnt.BusinessInterfaceStatus;
import com.online.taxi.consts.Const;
import com.online.taxi.consts.OrderTypeEnum;
import com.online.taxi.data.DriverData;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.map.Dispatch;
import com.online.taxi.dto.map.Vehicle;
import com.online.taxi.dto.map.request.DistanceRequest;
import com.online.taxi.dto.map.request.OrderRequest;
import com.online.taxi.dto.phone.BoundPhoneDto;
import com.online.taxi.dto.push.PushLoopBatchRequest;
import com.online.taxi.dto.push.PushRequest;
import com.online.taxi.entity.*;
import com.online.taxi.mapper.*;
import com.online.taxi.request.DispatchRequest;
import com.online.taxi.task.TaskCondition;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 */
@Slf4j
public class DispatchService {
    @Autowired
    private DriverService driverService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderRulePriceMapper orderRulePriceMapper;
    @Autowired
    private ConfigService configService;
    @Autowired
    private HttpService httpService;
    @Autowired
    private CarInfoMapper carInfoMapper;
    @Autowired
    private DriverBaseInfoMapper driverBaseInfoMapper;

    @Autowired
    private TagInfoMapper tagInfoMapper;
    @Autowired
    private ChargeRuleMapper chargeRuleMapper;
    @Value("${oss.file.url}")
    private String ossFileUrl;
    @Autowired
    private PassengerInfoMapper passengerInfoMapper;

    @Cache
    public String test2() {
        return "1233";
    }

    public PassengerInfo getPassengerInfo(int id) {
        return passengerInfoMapper.selectByPrimaryKey(id);
    }

    public String getChargeRuleStr(ChargeRule chargeRule) {
        String str = "";
        if (chargeRule != null) {
            str = chargeRule.getBaseMinutes().intValue() / 60 + "小时(含" + chargeRule.getBaseKilo().intValue() + "公里)";
        }
        return str;
    }

    public ChargeRule getChargeRule(String cityCode, int serviceTypeId, int carLevelId) {
        return chargeRuleMapper.getChargeRule(cityCode, serviceTypeId, carLevelId);
    }

    public TagInfo getTagInfo(int id) {
        return tagInfoMapper.selectByPrimaryKey(id);
    }

    public String getOssFileUrl() {
        return ossFileUrl;
    }

    public double getDriverEvaluateByDriverId(int driverId) {
        Double b = driverService.getDriverEvaluateByDriverId(driverId);
        if (b == null) {
            return 0;
        }
        return b;
    }

    public int pushMessage(PushRequest pushRequest) {
        ResponseResult r = httpService.pushMsg(pushRequest);
        if (r != null) {
            return r.getCode();
        }
        return 1;
    }

    public void updateAmapOrder(OrderRequest orderRequest) {
        httpService.updateAmapOrder(orderRequest);
    }

    public boolean isSpecial(String cityCode, int serviceTypeId, long time) {
        return configService.isSpecial(cityCode, serviceTypeId, time);
    }

    public void loopMessageBatch(PushLoopBatchRequest pushLoopBatchRequest) {
        httpService.loopMessageBatch(pushLoopBatchRequest);
    }

    //手机号绑定
    public BoundPhoneDto bindAxb(String phone1, String phone2, long expireTime) {
        return httpService.bind(phone1, phone2, expireTime);
        // try {
        //     return secretPhoneNumberService.bindAxb(phone1, phone2, new Date(expireTime));
        // } catch (ClientException e) {
        //     e.printStackTrace();
        // }
    }

    public void unbind(String subId, String secretNo) {
        httpService.unbind(subId, secretNo);
    }

    public double calDistance(String long1, String lat1, String long2, String lat2) {
        DistanceRequest distanceRequest = new DistanceRequest();
        distanceRequest.setOriginLongitude(long1);
        distanceRequest.setOriginLatitude(lat1);
        distanceRequest.setDestinationLongitude(long2);
        distanceRequest.setDestinationLatitude(lat2);
        return httpService.calDistance(distanceRequest);
    }

    public boolean updateOrder(Order order) {
        return httpService.updateOrder(order);
    }

    public List<TaskCondition> getForceTaskCondition(String cityCode, int serviceTypeId, int round) {
        CarDispatchDistributeIntervalSet carDispatchTimeThresholdSet = configService.getCarDispatchDistributeIntervalSet(cityCode, serviceTypeId);

        if (carDispatchTimeThresholdSet == null) {
            return null;
        }
        log.info("#order carDispatchTimeThresholdSet " + JSONObject.fromObject(carDispatchTimeThresholdSet).toString());
        List<TaskCondition> taskConditions = new ArrayList<>();
        for (int n = 0; n < round; n++) {
            for (int i = 0; i < 6; i++) {
                List<Integer> distanceList = new ArrayList<>();
                distanceList.add(2);
                distanceList.add(4);
                distanceList.add(6);
                TaskCondition taskCondition = new TaskCondition(carDispatchTimeThresholdSet.getCarServiceBeforeInterval(), carDispatchTimeThresholdSet.getCarServiceAfterInterval(), 0, 20, Integer.MAX_VALUE, distanceList, Const.COMPARE_TYPE_1);
                taskConditions.add(taskCondition);
            }
        }
        return taskConditions;
    }

    public List<TaskCondition> getSpecialCondition(String cityCode, int serviceTypeId) {
        List<TaskCondition> taskConditions = new ArrayList<>();

        CarDispatchDistributeIntervalSet carDispatchTimeThresholdSet = configService.getCarDispatchDistributeIntervalSet(cityCode, serviceTypeId);
        // CarDispatchDirectRouteOrderRadiusSet carDispatchDirectRouteOrderRadiusSet = configService.getCarDispatchDirectRouteOrderRadiusSet(cityCode, serviceTypeId);
        CarDispatchDistributeRadiusSet carDispatchDistributeRadiusSet = configService.getCarDispatchDistributeRadiusSet(cityCode, serviceTypeId);
        if (null == carDispatchDistributeRadiusSet) {
            return null;
        }

        log.info("#order carDispatchTimeThresholdSet " + JSONObject.fromObject(carDispatchTimeThresholdSet).toString());

        log.info("#order carDispatchDistributeRadiusSet " + JSONObject.fromObject(carDispatchDistributeRadiusSet).toString());

        TaskCondition taskCondition1 = new TaskCondition(carDispatchTimeThresholdSet.getCarServiceBeforeInterval(), carDispatchTimeThresholdSet.getCarServiceAfterInterval(), carDispatchDistributeRadiusSet.getMaxRadius(), 20, carDispatchDistributeRadiusSet.getMaxRadiusFirstPushDriverCount(), null, Const.COMPARE_TYPE_2);
        TaskCondition taskCondition2 = new TaskCondition(carDispatchTimeThresholdSet.getCarServiceBeforeInterval(), carDispatchTimeThresholdSet.getCarServiceAfterInterval(), carDispatchDistributeRadiusSet.getMaxRadius(), 20, carDispatchDistributeRadiusSet.getMaxRadiusFirstPushDriverCount(), null, Const.COMPARE_TYPE_2);
        TaskCondition taskCondition3 = new TaskCondition(carDispatchTimeThresholdSet.getCarServiceBeforeInterval(), carDispatchTimeThresholdSet.getCarServiceAfterInterval(), carDispatchDistributeRadiusSet.getMaxRadius(), 20, carDispatchDistributeRadiusSet.getMaxRadiusFirstPushDriverCount(), null, 0);
        TaskCondition taskCondition4 = new TaskCondition(carDispatchTimeThresholdSet.getCarServiceBeforeInterval(), carDispatchTimeThresholdSet.getCarServiceAfterInterval(), carDispatchDistributeRadiusSet.getMaxRadius(), 20, carDispatchDistributeRadiusSet.getMaxRadiusFirstPushDriverCount(), null, 0);
        taskConditions.add(taskCondition1);
        taskConditions.add(taskCondition2);
        taskConditions.add(taskCondition3);
        taskConditions.add(taskCondition4);
        return taskConditions;
    }

    public List<TaskCondition> getNormalCondition(String cityCode, int serviceTypeId) {
        List<TaskCondition> taskConditions = new ArrayList<>();

        CarDispatchDistributeIntervalSet carDispatchDistributeIntervalSet = configService.getCarDispatchDistributeIntervalSet(cityCode, serviceTypeId);
        // CarDispatchDirectRouteOrderRadiusSet carDispatchDirectRouteOrderRadiusSet = configService.getCarDispatchDirectRouteOrderRadiusSet(cityCode, serviceTypeId);
        CarDispatchDistributeRadiusSet carDispatchDistributeRadiusSet = configService.getCarDispatchDistributeRadiusSet(cityCode, serviceTypeId);
        if (null == carDispatchDistributeRadiusSet) {
            return null;
        }
        log.info("#order carDispatchDistributeRadiusSet " + JSONObject.fromObject(carDispatchDistributeRadiusSet).toString());
        TaskCondition taskCondition1 = new TaskCondition(carDispatchDistributeIntervalSet.getCarServiceBeforeInterval(), carDispatchDistributeIntervalSet.getCarServiceAfterInterval(), carDispatchDistributeRadiusSet.getMinRadius(), 20, carDispatchDistributeRadiusSet.getMinRadiusFirstPushDriverCount(), null, 0);
        TaskCondition taskCondition2 = new TaskCondition(carDispatchDistributeIntervalSet.getCarServiceBeforeInterval(), carDispatchDistributeIntervalSet.getCarServiceAfterInterval(), carDispatchDistributeRadiusSet.getMinRadius(), 20, carDispatchDistributeRadiusSet.getMinRadiusFirstPushDriverCount(), null, 0);
        TaskCondition taskCondition3 = new TaskCondition(carDispatchDistributeIntervalSet.getCarServiceBeforeInterval(), carDispatchDistributeIntervalSet.getCarServiceAfterInterval(), carDispatchDistributeRadiusSet.getMaxRadius(), 20, carDispatchDistributeRadiusSet.getMaxRadiusFirstPushDriverCount(), null, 0);
        TaskCondition taskCondition4 = new TaskCondition(carDispatchDistributeIntervalSet.getCarServiceBeforeInterval(), carDispatchDistributeIntervalSet.getCarServiceAfterInterval(), carDispatchDistributeRadiusSet.getMaxRadius(), 20, carDispatchDistributeRadiusSet.getMaxRadiusFirstPushDriverCount(), null, 0);
        taskConditions.add(taskCondition1);
        taskConditions.add(taskCondition2);
        taskConditions.add(taskCondition3);
        taskConditions.add(taskCondition4);
        return taskConditions;
    }

    public void sendSmsMessage(String phone, String code, Map<String, Object> map) {
        httpService.sendSms(phone, code, map);
    }

    public void sendSmsMessageHx(String phone, String code, String... content) {
        try {
            httpService.sendSms(phone, code, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int countDriverOrder(int id, Date startTime, Date endTime) {
        return orderMapper.countOrderByParam(id, startTime, endTime);
    }

    public ResponseResult dispatch(DispatchRequest dispatchRequest) {
        Order order = orderMapper.selectByPrimaryKey(Integer.parseInt(dispatchRequest.getOrderId()));
        OrderRulePrice orderRulePrice = orderRulePriceMapper.selectByOrderId(order.getId());
        if (order == null) {
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "订单 null");
        }
        if (orderRulePrice == null) {
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "订单orderRulePrice null");
        }
        CarDispatchDistributeIntervalSet carDispatchTimeThresholdSet = configService.getCarDispatchDistributeIntervalSet(orderRulePrice.getCityCode(), order.getServiceType());
        if (carDispatchTimeThresholdSet == null) {
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "carDispatchTimeThresholdSet null");
        }
        TaskCondition taskCondition = new TaskCondition(carDispatchTimeThresholdSet.getCarServiceBeforeInterval(), carDispatchTimeThresholdSet.getCarServiceAfterInterval(), dispatchRequest.getRadius(), -1, -1, null, -1);
        List<DriverData> list = getCarByOrder(order, taskCondition, dispatchRequest.getRadius(), new ArrayList<>(), -1, false);

        if (list == null) {
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "订单 list null");
        }
        // JSONObject result = new JSONObject();
        // result.put("status", 0);
        Dispatch dispatch = new Dispatch();
        List<Vehicle> vehicles = new ArrayList<>();
        for (DriverData driverData : list) {
            vehicles.add(driverData.getAmapVehicle());
        }
        dispatch.setCount(vehicles.size());
        dispatch.setOrderId(dispatchRequest.getOrderId());
        dispatch.setVehicles(vehicles);
        // result.put("data", dispatch);
        return ResponseResult.success(dispatch);
    }

    public List<DriverData> getCarByOrder(Order order, TaskCondition taskCondition, int distance, List<Integer> usedIds, int round, boolean searchType) {
        OrderRulePrice orderRulePrice = orderRulePriceMapper.selectByOrderId(order.getId());
        if (orderRulePrice == null) {
            log.info("#orderId = " + order.getId() + "  round = " + round + "orderRulePrice null");
            return null;
        }
        Timestamp startTime = new Timestamp(order.getOrderStartTime().getTime() - TimeUnit.MINUTES.toMillis(taskCondition.getFreeTimeBefor()));
        Timestamp endTime = new Timestamp(order.getOrderStartTime().getTime() + TimeUnit.MINUTES.toMillis(taskCondition.getFreeTimeAfter()));
        DispatchRequest dispatchRequest = new DispatchRequest(order.getId() + "", order.getDeviceCode(), order.getOrderType(), orderRulePrice.getCarLevelId(), orderRulePrice.getCityCode(), order.getStartTime().getTime(), order.getOrderStartTime().getTime(), order.getStartAddress(), order.getStartLongitude(), order.getStartLatitude(), order.getEndAddress(), order.getEndLongitude(), order.getEndLatitude(), distance, 200);
        List<DriverData> list = new ArrayList<>();
        ResponseResult<Dispatch> response = httpService.dispatch(dispatchRequest);
        int timeSort = 0;
        if (null == response.getData()) {
            log.info("#orderId = " + order.getId() + "  round = " + round + "调度返回null");
            return list;
        }
        if (response.getData().getCount() == 0) {
            log.info("#orderId = " + order.getId() + "  round = " + round + "调度数据空");
            return list;
        }
        for (Vehicle data : response.getData().getVehicles()) {
            try {
                int carId = Integer.parseInt(data.getVehicleId());
                DriverInfo driverInfo = driverService.getDriverByCarId(carId);
                CarInfo carInfo = getCarInfoById(carId);
                if (null == carInfo) {
                    log.info("#orderId = " + order.getId() + "  round = " + round + "carInfo null carId = " + carId);
                    continue;
                }
                if (driverInfo == null) {
                    log.info("#orderId = " + order.getId() + "  round = " + round + "driverInfo null driverId = " + driverInfo.getId());
                    continue;
                }
                if (!driverInfo.getCityCode().equals(orderRulePrice.getCityCode())) {
                    log.info("#orderId = " + order.getId() + "  round = " + round + " 城市不同 driverId = " + driverInfo.getId());
                    continue;
                }
                if (usedIds.contains(driverInfo.getId())) {
                    log.info("#orderId = " + order.getId() + "  round = " + round + "id 派过单 + id = " + driverInfo.getId());
                    continue;
                }
                DriverBaseInfo driverBaseInfo = driverBaseInfoMapper.selectByPrimaryKey(driverInfo.getId());
                if (driverBaseInfo == null) {
                    log.info("#orderId = " + order.getId() + "  round = " + round + "driverBase info null driverId=" + driverInfo.getId());
                    // continue;
                }
                if (searchType) {
                    if (!carInfo.getCarLevelId().equals(orderRulePrice.getCarLevelId())) {
                        log.info("#orderId = " + order.getId() + "  round = " + round + "车辆级别不同 driverId = " + driverInfo.getId());
                        continue;
                    }
                    if (StringUtils.isNotEmpty(order.getUserFeature())) {
                        if (StringUtils.isEmpty(driverInfo.getTags())) {
                            continue;
                        }
                        if (!Arrays.asList(driverInfo.getTags().split(",")).containsAll(Arrays.asList(order.getUserFeature().split(",")))) {
                            log.info("#orderId = " + order.getId() + "  round = " + round + " 司机标签不匹配陪 driverId = " + driverInfo.getId());
                            continue;
                        }
                    }
                }
                int workStatus = 0;
                if (driverInfo.getWorkStatus() != null) {
                    workStatus = driverInfo.getWorkStatus();
                }
                int csWorkStatus = 0;
                if (driverInfo.getCsWorkStatus() != null) {
                    csWorkStatus = driverInfo.getCsWorkStatus();
                }
                if (workStatus != Const.DRIVER_WORK_STATUS_GET_ORDER && workStatus != Const.DRIVER_WORK_STATUS_WORK && csWorkStatus != Const.DRIVER_CS_WORK_STATUS_WORK) {
                    log.info("#orderId = " + order.getId() + "  round = " + round + "  driverId = " + driverInfo.getId() + "司机工作状态=" + workStatus + "  车机工作状态=" + csWorkStatus);
                    continue;
                }
                int orderCount = orderMapper.countOrderByParam(driverInfo.getId(), startTime, endTime);
                if (orderCount > 0) {
                    log.info("#orderId = " + order.getId() + "  round = " + round + "  司机Id = " + driverInfo.getId() + "司机订单数=" + orderCount);
                    continue;
                }
                int driverOrderCount = driverService.getDriverOrderCount(driverInfo.getId());
                if (driverOrderCount > 0) {
                    log.info("#orderId = " + order.getId() + "  round = " + round + "  司机Id = " + driverInfo.getId() + "司机服务中， 订单数量 = " + driverOrderCount);
                    continue;
                }
                DriverData driverData = new DriverData();
                Integer isOpenOnTheWay = driverInfo.getIsFollowing();
                double homeDistance = Integer.MAX_VALUE;
                if (taskCondition.getCompareType() > 0 && driverBaseInfo != null && driverBaseInfo.getAddressLatitude() != null && !driverBaseInfo.getAddressLatitude().isEmpty() && driverBaseInfo.getAddressLongitude() != null && !driverBaseInfo.getAddressLongitude().isEmpty()) {
                    DistanceRequest distanceRequest = new DistanceRequest();
                    distanceRequest.setDestinationLatitude(driverBaseInfo.getAddressLatitude());
                    distanceRequest.setDestinationLongitude(driverBaseInfo.getAddressLongitude());
                    if (taskCondition.getCompareType() == Const.COMPARE_TYPE_2) {
                        distanceRequest.setOriginLatitude(order.getStartLatitude());
                        distanceRequest.setOriginLongitude(order.getStartLongitude());
                        homeDistance = httpService.calDistance(distanceRequest);
                        Integer compareDistance = configService.getGoHomeDistance(orderRulePrice.getCityCode(), orderRulePrice.getServiceTypeId(), taskCondition.getCompareType());
                        log.info("#orderId = " + order.getId() + " 配置home = " + compareDistance);
                        if (compareDistance != null && homeDistance > compareDistance * 1000) {
                            // homeDistance = Integer.MAX_VALUE;
                            log.info("#orderId = " + order.getId() + "  round = " + round + " driverId = " + driverInfo.getId() + " homeDistance = " + homeDistance);
                            continue;
                        }
                    } else if (taskCondition.getCompareType() == Const.COMPARE_TYPE_1) {
                        if (isOpenOnTheWay != null && isOpenOnTheWay == 1) {
                            distanceRequest.setOriginLatitude(order.getEndLatitude());
                            distanceRequest.setOriginLongitude(order.getEndLongitude());
                            homeDistance = httpService.calDistance(distanceRequest);
                            Integer compareDistance = configService.getGoHomeDistance(orderRulePrice.getCityCode(), OrderTypeEnum.FORCE.getCode(), taskCondition.getCompareType());
                            log.info("#orderId = " + order.getId() + " 配置home = " + compareDistance);
                            if (compareDistance != null) {
                                if (homeDistance / 1000 > compareDistance) {
                                    log.info("#orderId = " + order.getId() + "  round = " + round + " driverId = " + driverInfo.getId() + " homeDistance = " + homeDistance);
                                    homeDistance = Integer.MAX_VALUE;
                                    // continue;
                                } else {
                                    driverData.setIsFollowing(1);
                                }
                            }
                        }
                    }
                }
                driverData.setTimeSort(timeSort++);
                driverData.setHomeDistance(homeDistance);
                driverData.setAmapVehicle(data);
                driverData.setDriverInfo(driverInfo);
                driverData.setCarInfo(carInfo);
                list.add(driverData);
            } catch (Exception e) {
                log.error("Vehicle data", e);
            }
        }
        list.sort(new Comparator<DriverData>() {
            @Override
            public int compare(DriverData o1, DriverData o2) {
                int r = o1.getTimeSort() - o2.getTimeSort();
                int n = (int) (o1.getHomeDistance() - o2.getHomeDistance());
                return n == 0 ? r : n;
            }
        });
        return list;
    }

    public boolean hasDriver(String city, Date time, int carType, int serviceTypeId) {
        CarDispatchDistributeIntervalSet carDispatchDistributeIntervalSet = configService.getCarDispatchDistributeIntervalSet(city, serviceTypeId);
        if (carDispatchDistributeIntervalSet == null) {
            return false;
        }
        Date start = new Date(time.getTime() - TimeUnit.MINUTES.toMillis(carDispatchDistributeIntervalSet.getCarServiceBeforeInterval()));
        Date end = new Date(time.getTime() + TimeUnit.MINUTES.toMillis(carDispatchDistributeIntervalSet.getCarServiceAfterInterval()));
        int count = driverService.getWorkDriverCount(city, carType, start, end);
        int timeType = getDateTimeType(time);
        CarDispatchCapacitySet carDispatchCapacitySet = configService.getCarDispatchCapacitySet(city, timeType);

        if (carDispatchCapacitySet == null) {
            return false;
        }
        if (count > carDispatchCapacitySet.getSpareDriverCount()) {
            return true;
        }
        return false;
    }

    public boolean hasDriver2(String city, Date time, int carType, int serviceTypeId) {
        CarDispatchDistributeIntervalSet carDispatchDistributeIntervalSet = configService.getCarDispatchDistributeIntervalSet(city, serviceTypeId);
        if (carDispatchDistributeIntervalSet == null) {
            return false;
        }
        Date start = new Date(time.getTime() - TimeUnit.MINUTES.toMillis(carDispatchDistributeIntervalSet.getCarServiceBeforeInterval()));
        Date end = new Date(time.getTime() + TimeUnit.MINUTES.toMillis(carDispatchDistributeIntervalSet.getCarServiceAfterInterval()));
        int count = driverService.getWorkDriverCount(city, carType, start, end);
        List<CarDispatchCapacitySet> list = configService.getCarDispatchCapacitySetList(city);
        if (list == null || list.size() == 0) {
            return false;
        }
        int max = 0;
        int target = -1;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time.getTime());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        double t = Double.valueOf(hour + "." + minute);
        for (CarDispatchCapacitySet data : list) {
            if (data.getSpareDriverCount() > max) {
                max = data.getSpareDriverCount();
            }
            JSONObject jsonObject = JSONObject.fromObject(data.getCarServicePeriod());
            String s = jsonObject.getString("start");
            String e = jsonObject.getString("end");

            double ss = Double.valueOf(s.replace(":", "."));
            double ee = Double.valueOf(e.replace(":", "."));
            if (t >= ss && t < ee) {
                target = data.getSpareDriverCount();
                break;
            }
        }
        if (target == -1) {
            target = max;
        }
        log.info("#可服务司机数 = " + count);
        log.info("可服务司机配置数 = " + target);
        if (count > target) {
            return true;
        }
        return false;
    }

    public int getDateTimeType(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        if (h >= Const.DAY_TIME_START_NUM && h < Const.DAY_TIME_END_NUM) {
            return Const.DAY_TIME;
        } else {
            return Const.NIGHT;
        }
    }

    public void test(int id) {
        DriverInfo driverInfo = driverService.getDriverById(id);
        log.info(driverInfo.toString());
    }

    public Order getOrderById(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    public OrderRulePrice getOrderRulePrice(int orderId) {
        return orderRulePriceMapper.selectByOrderId(orderId);
    }

    public void updateDriverInfo(DriverInfo updateDriverInfo) {
        driverService.updateDriverInfo(updateDriverInfo);
    }

    public CarInfo getCarInfoById(int id) {
        return carInfoMapper.selectByPrimaryKey(id);
    }

    private static class LazyHodler {
        private static DispatchService ins = new DispatchService();
    }

    public static DispatchService ins() {
        return LazyHodler.ins;
    }
}
