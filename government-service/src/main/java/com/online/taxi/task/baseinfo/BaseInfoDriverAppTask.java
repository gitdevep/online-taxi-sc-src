package com.online.taxi.task.baseinfo;

import com.online.taxi.data.upload.proto.OTIpcDef;
import com.online.taxi.dto.baseinfo.BaseInfoDriverDto;
import com.online.taxi.mapper.BaseInfoMapper;
import com.online.taxi.task.AbstractSupervisionTask;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 驾驶员移动终端信息
 *
 * @date 2018/9/5
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BaseInfoDriverAppTask extends AbstractSupervisionTask {

    @NonNull
    private BaseInfoMapper baseInfoDriverMapper;

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
            BaseInfoDriverDto data = null;
            ipcType = OTIpcDef.IpcType.baseInfoDriverApp;
            try {
                data = baseInfoDriverMapper.getBaseInfoDriver(id);
                messageMap.put("LicenseId", data.getDrivingLicenceNumber());
                messageMap.put("DriverPhone", data.getPhoneNumber());
                messageMap.put("NetType", StringUtils.isEmpty(data.getMobileOperator()) ? 4 : data.getMobileOperator());
                messageMap.put("AppVersion", "1.0.0");
                messageMap.put("MapType", 2);
                messageMap.put("State", 0);
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
