package com.online.taxi.timingtask;

import com.online.taxi.entity.PassengerWalletFreezeRecord;
import com.online.taxi.mapper.PassengerWalletFreezeRecordMapper;
import com.online.taxi.task.OtherInterfaceTask;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 钱包解冻定时任务
 *
 * @date 2018/9/8
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PurseThawSchedule {

    @NonNull
    private PassengerWalletFreezeRecordMapper passengerWalletFreezeRecordMapper;

    @Autowired
    private OtherInterfaceTask otherInterfaceTask;

    @Scheduled(cron = "0 */2 *  * * ? ")
    private void doEveryMonthJob() {
        Date date = new Date();
        try{
            List<PassengerWalletFreezeRecord> passengerWalletFreezeRecordsList = passengerWalletFreezeRecordMapper.selectPurseThaw(date);
            log.info("待解冻记录：" + JSONArray.fromObject(passengerWalletFreezeRecordsList));
            for(int i=0;i<passengerWalletFreezeRecordsList.size();i++){
                PassengerWalletFreezeRecord passengerWalletFreezeRecord = passengerWalletFreezeRecordsList.get(i);
                log.info("解冻yid:" + passengerWalletFreezeRecord.getPassengerInfoId() + ",orderId:" + passengerWalletFreezeRecord.getOrderId());
                otherInterfaceTask.walletUnfreeze(passengerWalletFreezeRecord.getOrderId(),passengerWalletFreezeRecord.getPassengerInfoId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("定时任务{}发生错误", getClass().getName(), e);
        }
    }
}
