package com.online.taxi.task.baseinfo;

import com.online.taxi.data.upload.proto.OTIpcDef;
import com.online.taxi.entity.DriverOrderMessageStatistical;
import com.online.taxi.mapper.DriverOrderMessageStatisticalMapper;
import com.online.taxi.task.AbstractSupervisionTask;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 驾驶员统计信息
 *
 * @date 2018/9/8
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BaseInfoDriverStatTask extends AbstractSupervisionTask {

    @NonNull
    private DriverOrderMessageStatisticalMapper driverOrderMessageStatisticalMapper;

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
        return false;
    }

    private boolean execute(Integer id, int flag) {

        return tryComposeData(maxTimes, p -> {
            DriverOrderMessageStatistical data = null;
            ipcType = OTIpcDef.IpcType.baseInfoDriverStat;
            try {
                data = driverOrderMessageStatisticalMapper.selectByPrimaryKey(id);
                messageMap.put("LicenseId", data.getDrivingLicenceNumber());
                messageMap.put("Cycle", Integer.parseInt(data.getCycle()));
                messageMap.put("OrderCount", data.getOrderCount());
                messageMap.put("TrafficViolationCount", 0);
                messageMap.put("ComplainedCount", 0);
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
