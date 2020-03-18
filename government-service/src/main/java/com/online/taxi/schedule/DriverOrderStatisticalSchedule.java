package com.online.taxi.schedule;

import com.online.taxi.dto.baseinfo.DriverOrderMessageStatisticalDto;
import com.online.taxi.entity.DriverOrderMessageStatistical;
import com.online.taxi.mapper.BaseInfoMapper;
import com.online.taxi.mapper.DriverOrderMessageStatisticalMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 司机订单信息统计定时任务
 *
 * @date 2018/9/8
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DriverOrderStatisticalSchedule {

    @NonNull
    private BaseInfoMapper baseInfoMapper;

    @NonNull
    private DriverOrderMessageStatisticalMapper driverOrderMessageStatisticalMapper;

    @Scheduled(cron = "0 0 1 1 * ?")
    private void doEveryMonthJob() {
        try {
            String lastMonth = LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyyMM"));

            List<DriverOrderMessageStatisticalDto> list = baseInfoMapper.getDriverOrderMessageStatistical(lastMonth);
            for (DriverOrderMessageStatisticalDto dto : list) {
                DriverOrderMessageStatistical statistical = new DriverOrderMessageStatistical();
                statistical.setDriverId(dto.getDriverId());
                statistical.setDrivingLicenceNumber(dto.getDrivingLicenceNumber());
                statistical.setCycle(lastMonth);
                statistical.setOrderCount(dto.getOrderCount());
                driverOrderMessageStatisticalMapper.insertSelective(statistical);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("定时任务{}发生错误", getClass().getName(), e);
        }
    }

}
