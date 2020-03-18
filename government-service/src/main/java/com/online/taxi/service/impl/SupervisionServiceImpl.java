package com.online.taxi.service.impl;

import com.online.taxi.dto.baseinfo.*;
import com.online.taxi.dto.government.SupervisionData;
import com.online.taxi.dto.operate.OperatePayDto;
import com.online.taxi.dto.rated.RatedDriverPunishDto;
import com.online.taxi.entity.*;
import com.online.taxi.exception.ParameterException;
import com.online.taxi.service.SupervisionService;
import com.online.taxi.task.AbstractSupervisionTask;
import com.online.taxi.task.baseinfo.*;
import com.online.taxi.task.operate.OperateArriveTask;
import com.online.taxi.task.operate.OperateDepartTask;
import com.online.taxi.task.operate.OperateLoginLogoutTask;
import com.online.taxi.task.operate.OperatePayTask;
import com.online.taxi.task.order.OrderTask;
import com.online.taxi.task.position.PositionDriverTask;
import com.online.taxi.task.position.PositionVehicleTask;
import com.online.taxi.task.rated.RatedDriverPunishTask;
import com.online.taxi.task.rated.RatedDriverTask;
import com.online.taxi.task.rated.RatedPassengerComplaintTask;
import com.online.taxi.task.rated.RatedPassengerTask;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 监管上报服务
 *
 * @date 2018/8/23
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SupervisionServiceImpl implements SupervisionService {

    @NonNull
    private OrderTask orderTask;

    @NonNull
    private BaseInfoDriverTask baseInfoDriverTask;

    @NonNull
    private OperateDepartTask operateDepartTask;

    @NonNull
    private OperateArriveTask operateArriveTask;

    @NonNull
    private OperateLoginLogoutTask operateLoginLogoutTask;

    @NonNull
    private RatedPassengerTask ratedPassengerTask;

    @NonNull
    private RatedPassengerComplaintTask ratedPassengerComplaintTask;

    @NonNull
    private RatedDriverTask ratedDriverTask;

    @NonNull
    private BaseInfoDriverEducateTask baseInfoDriverEducateTask;

    @NonNull
    private BaseInfoCompanyFareTask baseInfoCompanyFareTask;

    @NonNull
    private BaseInfoCompanyPayTask baseInfoCompanyPayTask;

    @NonNull
    private BaseInfoCompanyPermitTask baseInfoCompanyPermitTask;

    @NonNull
    private BaseInfoCompanyServiceTask baseInfoCompanyServiceTask;

    @NonNull
    private BaseInfoCompanyStatTask baseInfoCompanyStatTask;

    @NonNull
    private OperatePayTask operatePayTask;

    @NonNull
    private BaseInfoCompanyTask baseInfoCompanyTask;

    @NonNull
    private BaseInfoDriverStatTask baseInfoDriverStatTask;

    @NonNull
    private BaseInfoDriverAppTask baseInfoDriverAppTask;

    @NonNull
    private BaseInfoVehicleTask baseInfoVehicleTask;

    @NonNull
    private BaseInfoVehicleTotalMileTask baseInfoVehicleTotalMileTask;

    @NonNull
    private BaseInfoPassengerTask baseInfoPassengerTask;

    @NonNull
    private PositionDriverTask positionDriverTask;

    @NonNull
    private PositionVehicleTask positionVehicleTask;

    @NonNull
    private BaseInfoVehicleInsuranceTask baseInfoVehicleInsuranceTask;

    @NonNull
    private RatedDriverPunishTask ratedDriverPunishTask;

    /**
     * 分发上报任务
     *
     * @param data 上报对象DTO
     */
    @Override
    public void dispatch(SupervisionData data) throws Exception {
        log.info("分发上报任务dispatch：{}", data);

        List<AbstractSupervisionTask> tasks = new ArrayList<>();
        Class cls = Class.forName(data.getClassName());

        if (Order.class == cls) {
            tasks.add(orderTask);
            tasks.add(operateDepartTask);
            tasks.add(operateArriveTask);
        } else if (DriverBaseInfo.class == cls || DriverInfo.class == cls) {
            if (DriverBaseInfo.class == cls) {
                tasks.add(baseInfoDriverAppTask);
            }
            tasks.add(baseInfoDriverTask);
            tasks.add(baseInfoDriverEducateTask);
        } else if (DriverWorkTime.class == cls) {
            tasks.add(operateLoginLogoutTask);
        } else if (EvaluateDriver.class == cls) {
            tasks.add(ratedPassengerTask);
            tasks.add(ratedPassengerComplaintTask);
        } else if (DriverRate.class == cls) {
            tasks.add(ratedDriverTask);
        } else if (BaseInfoCompanyFareDto.class == cls) {
            tasks.add(baseInfoCompanyFareTask);
        } else if (BaseInfoCompanyPayDto.class == cls) {
            tasks.add(baseInfoCompanyPayTask);
        } else if (BaseInfoCompanyPermitDto.class == cls) {
            tasks.add(baseInfoCompanyPermitTask);
        } else if (BaseInfoCompanyServiceDto.class == cls) {
            tasks.add(baseInfoCompanyServiceTask);
        } else if (BaseInfoCompanyStatDto.class == cls) {
            tasks.add(baseInfoCompanyStatTask);
        } else if (BaseInfoCompanyDto.class == cls) {
            tasks.add(baseInfoCompanyTask);
        } else if (DriverOrderMessageStatistical.class == cls) {
            tasks.add(baseInfoDriverStatTask);
        } else if (CarInfo.class == cls || CarBaseInfo.class == cls) {
            tasks.add(baseInfoVehicleTask);
            if (CarInfo.class == cls) {
                tasks.add(baseInfoVehicleTotalMileTask);
            }
        } else if (PassengerInfo.class == cls) {
            tasks.add(baseInfoPassengerTask);
        } else if (OperatePayDto.class == cls) {
            tasks.add(operatePayTask);
        } else if (OrderPoints.class == cls) {
            tasks.add(positionDriverTask);
            tasks.add(positionVehicleTask);
        } else if (BaseInfoVehicleInsuranceDto.class == cls) {
            tasks.add(baseInfoVehicleInsuranceTask);
        } else if (RatedDriverPunishDto.class == cls) {
            tasks.add(ratedDriverPunishTask);
        }

        if (!tasks.isEmpty()) {
            doTask(tasks, data.getOperationType(), data.getId());
        }
    }

    /**
     * 根据操作确定上报操作
     *
     * @param tasks         任务实例
     * @param operationType 操作类型
     * @param id            上报对象id
     */
    private void doTask(List<AbstractSupervisionTask> tasks, SupervisionData.OperationType operationType, Integer id) {
        switch (operationType) {
            case INSERT:
                tasks.forEach(t -> {
                    if (t.insert(id)) {
                        t.send();
                    }
                });
                break;

            case UPDATE:
                tasks.forEach(t -> {
                    if (t.update(id)) {
                        t.send();
                    }
                });
                break;

            case DELETE:
                tasks.forEach(t -> {
                    if (t.delete(id)) {
                        t.send();
                    }
                });
                break;

            default:
                throw new ParameterException("未知操作");
        }
    }
}
