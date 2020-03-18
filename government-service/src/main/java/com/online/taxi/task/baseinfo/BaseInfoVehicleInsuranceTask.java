package com.online.taxi.task.baseinfo;

import com.online.taxi.data.upload.proto.OTIpcDef;
import com.online.taxi.dto.baseinfo.BaseInfoVehicleInsuranceDto;
import com.online.taxi.mapper.BaseInfoMapper;
import com.online.taxi.task.AbstractSupervisionTask;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 车辆保险
 *
 * @date 2018/8/30
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BaseInfoVehicleInsuranceTask extends AbstractSupervisionTask {

    @NonNull
    private BaseInfoMapper baseInfoMapper;

    /**
     * 监听到插入操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean insert(Integer id) {
        return execute(id, 1);
    }

    /**
     * 监听到更新操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean update(Integer id) {
        return execute(id, 2);
    }

    /**
     * 监听到删除操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean delete(Integer id) {
        return execute(id, 3);
    }

    private boolean execute(Integer id, int flag) {

        return tryComposeData(maxTimes, p -> {
            BaseInfoVehicleInsuranceDto data = null;
            ipcType = OTIpcDef.IpcType.baseInfoVehicleInsurance;
            try {
                data = baseInfoMapper.getCarInsurance(id);
                messageMap.put("VehicleNo", data.getPlateNumber());
                messageMap.put("InsurCom", data.getInsuranceCompany());
                messageMap.put("InsurNum", data.getInsuranceNumber());
                messageMap.put("InsurType", data.getInsuranceType());
                messageMap.put("InsurCount", data.getInsuranceCount().intValue());
                messageMap.put("InsurEff", formatDateTime(data.getInsuranceEff(), DateTimePatternEnum.Date));
                messageMap.put("InsurExp", formatDateTime(data.getInsuranceExp(), DateTimePatternEnum.Date));
                messageMap.put("Flag", flag);
                messageMap.put("UpdateTime", now());
                return true;
            } catch (Exception e) {
                if (p == maxTimes && data != null) {
                    log.error("数据上报异常：ipcType={}, id={}", ipcType.name(), id, e);
                }
                return false;
            }
        });
    }
}
