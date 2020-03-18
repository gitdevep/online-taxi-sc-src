package com.online.taxi.task.baseinfo;

import com.online.taxi.data.upload.proto.OTIpcDef;
import com.online.taxi.dto.baseinfo.BaseInfoDriverDto;
import com.online.taxi.mapper.BaseInfoMapper;
import com.online.taxi.task.AbstractSupervisionTask;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 驾驶员培训信息
 *
 * @date 2018/9/3
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BaseInfoDriverEducateTask extends AbstractSupervisionTask {

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
            ipcType = OTIpcDef.IpcType.baseInfoDriverEducate;
            try {
                data = baseInfoDriverMapper.getBaseInfoDriver(id);
                messageMap.put("LicenseId", data.getDrivingLicenceNumber());
                messageMap.put("CourseName", data.getTrainingCourses());
                messageMap.put("CourseDate", formatDateTime(data.getTrainingCoursesDate(), DateTimePatternEnum.Date));
                messageMap.put("StartTime", "" + formatDateTime(data.getTrainingCoursesStartDate(), DateTimePatternEnum.DateTime));
                messageMap.put("StopTime", "" + formatDateTime(data.getTrainingCoursesEndDate(), DateTimePatternEnum.DateTime));
                messageMap.put("Duration", data.getTrainingCoursesTime());
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
