package com.online.taxi.task.operate;

import com.online.taxi.data.upload.proto.OTIpcDef;
import com.online.taxi.dto.operate.OperatePayDto;
import com.online.taxi.mapper.OperateMapper;
import com.online.taxi.task.AbstractSupervisionTask;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 经营支付接口
 *
 * @date 2018/8/31
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OperatePayTask extends AbstractSupervisionTask {
    @NonNull
    private OperateMapper operateMapper;

    @Override
    public boolean insert(Integer id) {
        return execute(id);
    }

    @Override
    public boolean update(Integer id) {
        return execute(id);
    }

    @Override
    public boolean delete(Integer id) {
        return execute(id);
    }

    private boolean execute(Integer id) {

        return tryComposeData(maxTimes, p -> {
            OperatePayDto data = null;
            ipcType = OTIpcDef.IpcType.operatePay;
            try {
                data = operateMapper.selectOperatorPay(id);
                messageMap.put("OrderId", data.getOrderNumber());
                messageMap.put("OnArea", data.getCityCode());
                messageMap.put("LicenseId", data.getDrivingLicenceNumber());
                messageMap.put("FareType", data.getRuleId());
                messageMap.put("VehicleNo", data.getPlateNumber());
                messageMap.put("BookDepTime", formatDateTime(data.getOrderStartTime(), DateTimePatternEnum.DateTime));
                messageMap.put("DepLongitude", toCoordinates(data.getReceivePassengerLongitude()));
                messageMap.put("DepLatitude", toCoordinates(data.getReceivePassengerLatitude()));
                messageMap.put("DepTime", formatDateTime(data.getReceivePassengerTime(), DateTimePatternEnum.DateTime));
                messageMap.put("DestLongitude", toCoordinates(data.getPassengerGetoffLongitude()));
                messageMap.put("DestLatitude", toCoordinates(data.getPassengerGetoffLatitude()));
                messageMap.put("DestTime", formatDateTime(data.getPassengerGetoffTime(), DateTimePatternEnum.DateTime));
                messageMap.put("DriveMile", data.getTotalDistance().longValue());
                messageMap.put("DriveTime", data.getTotalTime().longValue() * 60);
                messageMap.put("FactPrice", data.getTotalPrice());
                messageMap.put("FarUpPrice", data.getBeyondPrice());
                messageMap.put("OtherUpPrice", data.getParkingPrice() + data.getOtherPrice() + data.getRoadPrice());
                messageMap.put("PayState", "1");
                messageMap.put("InvoiceStatus", data.getInvoiceType() != null && data.getInvoiceType() == 4 ? "1" : "0");
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
