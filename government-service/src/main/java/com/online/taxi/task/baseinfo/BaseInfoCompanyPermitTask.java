package com.online.taxi.task.baseinfo;

import com.online.taxi.data.upload.proto.OTIpcDef;
import com.online.taxi.dto.baseinfo.BaseInfoCompanyPermitDto;
import com.online.taxi.mapper.BaseInfoMapper;
import com.online.taxi.task.AbstractSupervisionTask;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 网约车平台公司经营许可证
 *
 * @date 2018/8/29
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BaseInfoCompanyPermitTask extends AbstractSupervisionTask {
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

    private boolean execute(Integer id, int flag) {

        return tryComposeData(maxTimes, p -> {
            BaseInfoCompanyPermitDto dto = null;
            ipcType = OTIpcDef.IpcType.baseInfoCompanyPermit;
            try {
                dto = baseInfoMapper.getBaseInfoCompanyPermitDto(id);
                messageMap.put("Certificate", dto.getCertificate());
                messageMap.put("OperationArea", dto.getOperationArea());
                messageMap.put("OwnerName", dto.getOwnerName());
                messageMap.put("Organization", dto.getOrganization());
                messageMap.put("StartDate", formatDateTime(dto.getStartDate(), DateTimePatternEnum.Date));
                messageMap.put("StopDate", formatDateTime(dto.getStopDate(), DateTimePatternEnum.Date));
                messageMap.put("CertifyDate", formatDateTime(dto.getCertifyDate(), DateTimePatternEnum.Date));
                messageMap.put("State", dto.getState());
                messageMap.put("Flag", flag);
                messageMap.put("UpdateTime", now());
                return true;
            } catch (Exception e) {
                if (p == maxTimes && dto != null) {
                    log.error("数据上报异常：ipcType={}, id={}", ipcType.name(), id, e);
                }
                return false;
            }
        });
    }
}
