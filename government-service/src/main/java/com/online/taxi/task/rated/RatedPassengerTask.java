package com.online.taxi.task.rated;

import com.online.taxi.data.upload.proto.OTIpcDef;
import com.online.taxi.dto.rated.RatedPassengerDto;
import com.online.taxi.mapper.RatedMapper;
import com.online.taxi.task.AbstractSupervisionTask;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 乘客评价信息控制器
 *
 * @date 2018/9/1
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RatedPassengerTask extends AbstractSupervisionTask {

    @NonNull
    private RatedMapper ratedMapper;

    /**
     * 监听到插入操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean insert(Integer id) {
        return generate(id);
    }

    /**
     * 监听到更新操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean update(Integer id) {
        return generate(id);
    }

    /**
     * 监听到删除操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean delete(Integer id) {
        return generate(id);
    }

    /**
     * 检索数据
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    private boolean generate(Integer id) {

        return tryComposeData(maxTimes, p -> {
            RatedPassengerDto data = null;
            ipcType = OTIpcDef.IpcType.ratedPassenger;
            try {
                data = ratedMapper.selectRatedPassenger(id);
                messageMap.put("OrderId", data.getOrderNumber());
                messageMap.put("EvaluateTime", formatDateTime(data.getUpdateTime(), DateTimePatternEnum.DateTime));
                messageMap.put("ServiceScore", data.getGrade());
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
