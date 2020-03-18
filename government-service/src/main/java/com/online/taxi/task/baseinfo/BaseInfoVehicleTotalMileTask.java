package com.online.taxi.task.baseinfo;

import com.online.taxi.data.upload.proto.OTIpcDef;
import com.online.taxi.dto.baseinfo.BaseInfoVehicleTotalMileDto;
import com.online.taxi.mapper.BaseInfoMapper;
import com.online.taxi.task.AbstractSupervisionTask;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 网约车车辆里程信息
 *
 * @date 2018/9/11
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BaseInfoVehicleTotalMileTask extends AbstractSupervisionTask {

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
        return execute(baseInfoMapper.getVehicleTotalMile(id), 1);
    }

    /**
     * 监听到更新操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean update(Integer id) {
        return execute(baseInfoMapper.getVehicleTotalMile(id), 2);
    }

    /**
     * 监听到删除操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean delete(Integer id) {
        return execute(baseInfoMapper.getVehicleTotalMile(id), 3);
    }

    private boolean execute(BaseInfoVehicleTotalMileDto data, int flag) {

        return tryComposeData(maxTimes, p -> {
            ipcType = OTIpcDef.IpcType.baseInfoVehicleTotalMile;
            try {
                messageMap.put("VehicleNo", data.getPlateNumber());
                messageMap.put("TotalMile", data.getTotalMile());
                messageMap.put("Flag", flag);
                messageMap.put("UpdateTime", now());
                return true;
            } catch (Exception e) {
                if (p == maxTimes && data != null) {
                    log.error("数据上报异常：ipcType={}, data={}", ipcType.name(), data, e);
                }
                return false;
            }
        });
    }
}
