package com.online.taxi.task.baseinfo;

import com.online.taxi.data.upload.proto.OTIpcDef;
import com.online.taxi.dto.baseinfo.BaseInfoCompanyServiceDto;
import com.online.taxi.mapper.BaseInfoMapper;
import com.online.taxi.task.AbstractSupervisionTask;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 网约车平台公司服务机构
 *
 * @date 2018/8/29
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BaseInfoCompanyServiceTask extends AbstractSupervisionTask {
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
            BaseInfoCompanyServiceDto dto = null;
            ipcType = OTIpcDef.IpcType.baseInfoCompanyService;
            try {
                dto = baseInfoMapper.getBaseInfoCompanyServiceDto(id);
                messageMap.put("ServiceName", dto.getServiceName());
                messageMap.put("ServiceNo", dto.getServiceNo());
                messageMap.put("DetailAddress", dto.getDetailAddress());
                messageMap.put("ResponsibleName", dto.getResponsibleName());
                messageMap.put("ResponsiblePhone", dto.getResponsiblePhone());
                messageMap.put("ManagerName", dto.getManagerName());
                messageMap.put("ManagerPhone", dto.getManagerPhone());
                messageMap.put("ContactPhone", dto.getContactPhone());
                messageMap.put("MailAddress", dto.getMailAddress());
                messageMap.put("CreateDate", formatDateTime(dto.getCreateDate(), DateTimePatternEnum.Date));
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
