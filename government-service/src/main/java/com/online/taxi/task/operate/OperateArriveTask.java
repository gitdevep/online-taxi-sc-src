package com.online.taxi.task.operate;

import com.online.taxi.data.upload.proto.OTIpcDef;
import com.online.taxi.dto.operate.OperateDto;
import com.online.taxi.mapper.OperateMapper;
import com.online.taxi.task.AbstractSupervisionTask;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 经营到达
 *
 * @date 2018/8/30
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OperateArriveTask extends AbstractSupervisionTask {

    @NonNull
    private OperateMapper operateMapper;

    /**
     * 监听到插入操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean insert(Integer id) {
        return false;
    }

    /**
     * 监听到更新操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean update(Integer id) {
        return execute(id);
    }

    /**
     * 监听到删除操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean delete(Integer id) {
        return false;
    }

    private boolean execute(Integer id) {

        return tryComposeData(maxTimes, p -> {
            OperateDto data = null;
            ipcType = OTIpcDef.IpcType.operateArrive;
            try {
                data = operateMapper.selectArrival(id);
                messageMap.put("OrderId", data.getOrderNumber());
                messageMap.put("DestLongitude", toCoordinates(data.getPassengerGetoffLongitude()));
                messageMap.put("DestLatitude", toCoordinates(data.getPassengerGetoffLatitude()));
                messageMap.put("Encrypt", 1);
                messageMap.put("DestTime", formatDateTime(data.getPassengerGetoffTime(), DateTimePatternEnum.DateTime));
                messageMap.put("DriveMile", data.getTotalDistance().longValue());
                messageMap.put("DriveTime", data.getTotalTime().longValue() * 60);
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



