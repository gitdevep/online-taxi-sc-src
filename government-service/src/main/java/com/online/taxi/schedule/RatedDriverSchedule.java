package com.online.taxi.schedule;

import com.online.taxi.dto.rated.RatedDriverDto;
import com.online.taxi.entity.DriverRate;
import com.online.taxi.mapper.DriverRateMapper;
import com.online.taxi.mapper.RatedMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 驾驶员信誉信息定时任务
 *
 * @date 2018/9/1
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RatedDriverSchedule {

    @NonNull
    private RatedMapper ratedMapper;

    @NonNull
    private DriverRateMapper driverRateMapper;

    @Scheduled(cron = "0 0 1 1 * ?")
    private void doEveryMonthJob() {
        Date today = new Date();
        try {
            List<RatedDriverDto> ratedDrivers = ratedMapper.selectRatedDrivers();
            for (RatedDriverDto ratedDriver : ratedDrivers) {
                DriverRate driverRate = new DriverRate();
                driverRate.setDriverId(ratedDriver.getDriverId());
                driverRate.setGrade(ratedDriver.getGrade());
                driverRate.setTestDate(today);
                driverRate.setTestDepartment("运管部");
                driverRateMapper.insertSelective(driverRate);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("定时任务{}发生错误", getClass().getName(), e);
        }
    }
}
