package com.online.taxi.task.baseinfo;

import com.online.taxi.data.upload.proto.OTIpcDef;
import com.online.taxi.dto.baseinfo.BaseInfoCompanyStatDto;
import com.online.taxi.mapper.BaseInfoMapper;
import com.online.taxi.task.AbstractSupervisionTask;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 网约车平台运营规模信息
 *
 * @date 2018/8/29
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BaseInfoCompanyStatTask extends AbstractSupervisionTask {

    @NonNull
    private BaseInfoMapper baseInfoMapper;

    @Override
    public boolean insert(Integer id) {
        return execute(id, 1);
    }

    @Override
    public boolean update(Integer id) {
        return execute(id, 2);
    }

    @Override
    public boolean delete(Integer id) {
        return execute(id, 3);
    }

    @Override
    public long toCoordinates(String coordinate) {
        return 0;
    }

    private boolean execute(Integer id, int flag) {

        return tryComposeData(maxTimes, p -> {
            BaseInfoCompanyStatDto data = null;
            ipcType = OTIpcDef.IpcType.baseInfoCompanyStat;
            try {
                data = baseInfoMapper.getBaseInfoCompanyStatDto();
                messageMap.put("VehicleNum", data.getVehicleNum());
                messageMap.put("DriverNum", data.getDriverNum());
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
