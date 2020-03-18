package com.online.taxi.task.baseinfo;

import com.online.taxi.data.upload.proto.OTIpcDef;
import com.online.taxi.dto.baseinfo.BaseInfoCompanyPayDto;
import com.online.taxi.mapper.BaseInfoMapper;
import com.online.taxi.task.AbstractSupervisionTask;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 网约车平台公司支付信息
 *
 * @date 2018/8/29
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BaseInfoCompanyPayTask extends AbstractSupervisionTask {
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
            BaseInfoCompanyPayDto data = null;
            ipcType = OTIpcDef.IpcType.baseInfoCompanyPay;
            try {
                data = baseInfoMapper.getBaseInfoCompanyPayDto(id);
                messageMap.put("PayName", data.getPayName());
                messageMap.put("PayId", data.getPayId());
                messageMap.put("PayType", data.getPayType());
                messageMap.put("PayScope", data.getPayScope());
                messageMap.put("PrepareBank", data.getPrepareBank());
                messageMap.put("CountDate", data.getCountDate());
                messageMap.put("State", data.getState());
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
