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
 * 司机经营出车、收车
 *
 * @date 2018/8/31
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OperateLoginLogoutTask extends AbstractSupervisionTask {

    @NonNull
    private OperateMapper operateMapper;

    @Override
    public boolean insert(Integer id) {
        ipcType = OTIpcDef.IpcType.operateLogin;
        return execute(id, 1);
    }

    @Override
    public boolean update(Integer id) {
        ipcType = OTIpcDef.IpcType.operateLogout;
        return execute(id, 2);
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    private boolean execute(Integer id, int flag) {

        return tryComposeData(maxTimes, p -> {
            OperateDto data = null;
            try {
                data = operateMapper.selectDriverLoginLogout(id);
                messageMap.put("LicenseId", data.getDrivingLicenceNumber());
                messageMap.put("VehicleNo", data.getPlateNumber());
                if (flag == 1) {
                    messageMap.put("LoginTime", formatDateTime(data.getWorkStart(), DateTimePatternEnum.DateTime));
                } else {
                    messageMap.put("LogoutTime", formatDateTime(data.getWorkEnd(), DateTimePatternEnum.DateTime));
                }
                messageMap.put("Encrypt", 1);
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
