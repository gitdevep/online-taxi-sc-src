package com.online.taxi.task.baseinfo;

import com.online.taxi.data.upload.proto.OTIpcDef;
import com.online.taxi.dto.baseinfo.BaseInfoCompanyFareDto;
import com.online.taxi.mapper.BaseInfoMapper;
import com.online.taxi.task.AbstractSupervisionTask;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 网约车平台运价信息
 *
 * @date 2018/8/29
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BaseInfoCompanyFareTask extends AbstractSupervisionTask {

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
            BaseInfoCompanyFareDto data = null;
            ipcType = OTIpcDef.IpcType.baseInfoCompanyFare;
            try {
                data = baseInfoMapper.getBaseInfoCompanyFareById(id);
                messageMap.put("FareType", "" + data.getId());
                messageMap.put("FareValidOn", formatDateTime(data.getEffectiveTime(), DateTimePatternEnum.DateTime));
                messageMap.put("StartFare", data.getBasePrice().intValue());
                messageMap.put("StartMile", data.getBaseKilo().intValue());
                messageMap.put("UnitPricePerMile", data.getPerKiloPrice());
                messageMap.put("UnitPricePerMinute", data.getPerMinutePrice());
                messageMap.put("MorningPeakTimeOn", "0700");
                messageMap.put("MorningPeakTimeOff", "0900");
                messageMap.put("EveningPeakTimeOn", "1700");
                messageMap.put("EveningPeakTimeOff", "1900");
                messageMap.put("PeakUnitPrice", 0);
                messageMap.put("PeakPriceStartMile", 0);
                messageMap.put("State", flag == 3 ? 1 : 0);
                messageMap.put("UpdateTime", now());
                messageMap.put("Flag", flag);
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
