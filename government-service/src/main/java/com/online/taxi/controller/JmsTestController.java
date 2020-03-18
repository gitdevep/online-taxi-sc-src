package com.online.taxi.controller;

import com.online.taxi.dto.baseinfo.*;
import com.online.taxi.dto.government.SupervisionData;
import com.online.taxi.dto.operate.OperateDto;
import com.online.taxi.dto.operate.OperatePayDto;
import com.online.taxi.dto.position.PositionDriverDto;
import com.online.taxi.dto.rated.RatedDriverDto;
import com.online.taxi.dto.rated.RatedDriverPunishDto;
import com.online.taxi.dto.rated.RatedPassengerDto;
import com.online.taxi.entity.*;
import com.online.taxi.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试用controller
 *
 * @date 2018/9/18
 */
@RestController
public class JmsTestController extends AbstractJmsController {

    @Autowired
    private TestMapper mapper;

    @GetMapping("/all")
    public void run() throws Exception {
        // getBaseInfoCompany();
        // getBaseInfoCompanyStat();
        // getBaseInfoCompanyPay();
        // getBaseInfoCompanyService();
        // getBaseInfoCompanyPermit();
        // getBaseInfoCompanyFare();
        // getBaseInfoVehicle();
        // getBaseInfoVehicleInsurance();
        // getBaseInfoVehicleTotalMile();
        // getBaseInfoDriver();
        // getBaseInfoDriveEducate();
        // getBaseInfoDriverApp();
        // getBaseInfoDriverStat();
        // getBaseInfoPassenger();
        // getOrder();
        // getOperateLoginLogout();
        // getOperatePay();
        // getPosition();
        // getRatedPassenger();
        getRatedDriverPunish();
        // getRatedDriver();
    }

    /**
     * 表A.4 网约车平台公司基本信息
     */
    private void getBaseInfoCompany() throws Exception {
        List<BaseInfoCompany> list = mapper.selectBaseInfoCompany();
        for (BaseInfoCompany obj : list) {
            triggerListener(BaseInfoCompanyDto.class, obj.getId().toString(), SupervisionData.OperationType.UPDATE.name());
        }
    }

    /**
     * 表A.6 网约车平台公司营运规模信息
     */
    private void getBaseInfoCompanyStat() throws Exception {
        triggerListener(BaseInfoCompanyStatDto.class, "1", SupervisionData.OperationType.UPDATE.name());
    }

    /**
     * 表A.8 网约车平台公司支付信息
     */
    private void getBaseInfoCompanyPay() throws Exception {
        List<BaseInfoCompanyPayDto> list = mapper.selectBaseInfoCompanyPay();
        for (BaseInfoCompanyPayDto obj : list) {
            triggerListener(BaseInfoCompanyPayDto.class, obj.getId().toString(), SupervisionData.OperationType.UPDATE.name());
        }
    }

    /**
     * 表A.10 网约车平台公司服务机构 全表
     */
    private void getBaseInfoCompanyService() throws Exception {
        List<BaseInfoCompanyServiceDto> list = mapper.selectBaseInfoCompanyService();
        for (BaseInfoCompanyServiceDto obj : list) {
            triggerListener(BaseInfoCompanyServiceDto.class, obj.getId().toString(), SupervisionData.OperationType.UPDATE.name());
        }
    }

    /**
     * 表A.12 网约车平台公司经营许可
     */
    private void getBaseInfoCompanyPermit() throws Exception {
        List<BaseInfoCompanyPermitDto> list = mapper.selectBaseInfoCompanyPermit();
        for (BaseInfoCompanyPermitDto obj : list) {
            triggerListener(BaseInfoCompanyPermitDto.class, obj.getId().toString(), SupervisionData.OperationType.UPDATE.name());
        }
    }

    /**
     * 表A.14 网约车平台公司运价信息
     */
    private void getBaseInfoCompanyFare() throws Exception {
        List<BaseInfoCompanyFareDto> list = mapper.selectBaseInfoCompanyFare();
        for (BaseInfoCompanyFareDto obj : list) {
            triggerListener(BaseInfoCompanyFareDto.class, obj.getId().toString(), SupervisionData.OperationType.UPDATE.name());
        }
    }

    /**
     * 表A.16 车辆基本信息
     */
    private void getBaseInfoVehicle() throws Exception {
        List<BaseInfoVehicleDto> list = mapper.selectBaseInfoVehicle();
        for (BaseInfoVehicleDto obj : list) {
            triggerListener(CarBaseInfo.class, "4", SupervisionData.OperationType.UPDATE.name());
        }
    }

    /**
     * A.18 车辆保险信息
     */
    private void getBaseInfoVehicleInsurance() throws Exception {
        List<BaseInfoVehicleInsuranceDto> list = mapper.selectCarInsurance();
        for (BaseInfoVehicleInsuranceDto obj : list) {
            triggerListener(BaseInfoVehicleInsuranceDto.class, obj.getId().toString(), SupervisionData.OperationType.UPDATE.name());
        }
    }

    /**
     * A.20 网约车车辆里程信息
     */
    private void getBaseInfoVehicleTotalMile() throws Exception {
        List<BaseInfoVehicleTotalMileDto> list = mapper.selectVehicleTotalMile();
        for (BaseInfoVehicleTotalMileDto obj : list) {
            triggerListener(CarInfo.class, obj.getId().toString(), SupervisionData.OperationType.UPDATE.name());
        }
    }

    /**
     * A.22 驾驶员基本信息
     */
    private void getBaseInfoDriver() throws Exception {
        List<BaseInfoDriverDto> list = mapper.selectBaseInfoDriver();
        for (BaseInfoDriverDto obj : list) {
            triggerListener(DriverInfo.class, obj.getId().toString(), SupervisionData.OperationType.UPDATE.name());
        }
    }

    /**
     * A.24 网约车驾驶员培训信息
     */
    private void getBaseInfoDriveEducate() throws Exception {
        List<BaseInfoDriverDto> list = mapper.selectBaseInfoDriver();
        for (BaseInfoDriverDto obj : list) {
            triggerListener(DriverInfo.class, obj.getId().toString(), SupervisionData.OperationType.UPDATE.name());
        }
    }

    /**
     * A.26 驾驶员移动终端信息
     */
    private void getBaseInfoDriverApp() throws Exception {
        List<BaseInfoDriverDto> list = mapper.selectBaseInfoDriver();
        for (BaseInfoDriverDto obj : list) {
            triggerListener(DriverBaseInfo.class, obj.getId().toString(), SupervisionData.OperationType.UPDATE.name());
        }
    }

    /**
     * A.28 网约车驾驶员统计信息
     */
    private void getBaseInfoDriverStat() throws Exception {
        List<DriverOrderMessageStatistical> list = mapper.selectDriverOrderMessageStatistical();
        for (DriverOrderMessageStatistical obj : list) {
            triggerListener(DriverOrderMessageStatistical.class, obj.getId().toString(), SupervisionData.OperationType.UPDATE.name());
        }
    }

    /**
     * A.30 乘客基本信息
     */
    private void getBaseInfoPassenger() throws Exception {
        List<PassengerInfo> list = mapper.selectPassengerInfo();
        for (PassengerInfo obj : list) {
            triggerListener(PassengerInfo.class, obj.getId().toString(), SupervisionData.OperationType.UPDATE.name());
        }
    }

    /**
     * A.32 订单发起
     * A.34 订单成功
     * A.36 订单撤销
     * <p>
     * A.42 经营出发
     * A.44 经营到达
     */
    private void getOrder() throws Exception {
        List<Order> list = mapper.selectOrder();
        for (Order obj : list) {
            triggerListener(Order.class, obj.getId().toString(), SupervisionData.OperationType.UPDATE.name());
        }
    }

    /**
     * A.38 车辆运营上线
     * A.40 车辆运营下线
     */
    private void getOperateLoginLogout() throws Exception {
        List<OperateDto> list = mapper.selectDriverLoginLogout();
        for (OperateDto obj : list) {
            triggerListener(DriverWorkTime.class, obj.getId().toString(), SupervisionData.OperationType.UPDATE.name());
        }
    }

    /**
     * A.46 经营支付
     */
    private void getOperatePay() throws Exception {
        List<OperatePayDto> list = mapper.selectOperatorPay();
        for (OperatePayDto obj : list) {
            triggerListener(OperatePayDto.class, obj.getOrderId().toString(), SupervisionData.OperationType.UPDATE.name());
        }
    }

    /**
     * A.48 驾驶员定位信息
     * A.50 车辆定位信息
     */
    private void getPosition() throws Exception {
        List<PositionDriverDto> list = mapper.selectPosition();
        for (PositionDriverDto obj : list) {
            triggerListener(OrderPoints.class, obj.getId().toString(), SupervisionData.OperationType.UPDATE.name());
        }
    }

    /**
     * A.52 乘客评价信息
     * A.54 乘客投诉信息
     */
    private void getRatedPassenger() throws Exception {
        List<RatedPassengerDto> list = mapper.selectRatedPassenger();
        for (RatedPassengerDto obj : list) {
            triggerListener(EvaluateDriver.class, obj.getId().toString(), SupervisionData.OperationType.UPDATE.name());
        }
    }

    /**
     * A.56 驾驶员处罚信息
     */
    private void getRatedDriverPunish() throws Exception {
        List<RatedDriverPunishDto> list = mapper.selectRatedDriverPunish();
        for (RatedDriverPunishDto obj : list) {
            triggerListener(RatedDriverPunishDto.class, obj.getId().toString(), SupervisionData.OperationType.UPDATE.name());
        }
    }

    /**
     * A.58 驾驶员信誉信息
     */
    private void getRatedDriver() throws Exception {
        List<RatedDriverDto> list = mapper.selectRatedDriver();
        for (RatedDriverDto obj : list) {
            triggerListener(DriverRate.class, obj.getId().toString(), SupervisionData.OperationType.UPDATE.name());
        }
    }
}
