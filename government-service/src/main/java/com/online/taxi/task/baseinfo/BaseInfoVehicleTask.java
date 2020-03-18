package com.online.taxi.task.baseinfo;

import com.online.taxi.data.upload.proto.OTIpcDef;
import com.online.taxi.dto.baseinfo.BaseInfoVehicleDto;
import com.online.taxi.mapper.BaseInfoMapper;
import com.online.taxi.task.AbstractSupervisionTask;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 车辆基本信息
 *
 * @date 2018/8/29
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class BaseInfoVehicleTask extends AbstractSupervisionTask {

    @NonNull
    private BaseInfoMapper baseInfoMapper;

    @Override
    public boolean insert(Integer id) {
        return execute(baseInfoMapper.getBaseInfoVehicle(id), 1);
    }

    /**
     * 监听到更新操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean update(Integer id) {
        return execute(baseInfoMapper.getBaseInfoVehicle(id), 2);
    }

    /**
     * 监听到删除操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean delete(Integer id) {
        return execute(baseInfoMapper.getBaseInfoVehicle(id), 3);
    }

    /**
     * 组装上报数据
     *
     * @param data 车辆基本信息DTO
     * @param flag 操作标识
     * @return 是否为合法上报数据
     */
    private boolean execute(BaseInfoVehicleDto data, int flag) {

        return tryComposeData(maxTimes, p -> {
            ipcType = OTIpcDef.IpcType.baseInfoVehicle;
            try {
                messageMap.put("VehicleNo", data.getPlateNumber());
                messageMap.put("PlateColor", data.getPlateColor());
                messageMap.put("Seats", data.getSeats());
                messageMap.put("Brand", data.getBrand());
                messageMap.put("Model", data.getModel());
                messageMap.put("VehicleType", data.getCarBaseType());
                messageMap.put("OwnerName", data.getCarOwner());
                messageMap.put("VehicleColor", data.getColor());
                messageMap.put("EngineId", data.getEngineNumber());
                messageMap.put("VIN", data.getVinNumber());
                messageMap.put("CertifyDateA", formatDateTime(data.getRegisterTime(), DateTimePatternEnum.Date));
                messageMap.put("FuelType", data.getFuelType());
                messageMap.put("EngineDisplace", data.getEngineCapacity());
                messageMap.put("TransAgency", data.getTransportIssuingAuthority());
                messageMap.put("TransArea", data.getBusinessArea());
                messageMap.put("TransDateStart", formatDateTime(data.getTransportCertificateValidityStart(), DateTimePatternEnum.Date));
                messageMap.put("TransDateStop", formatDateTime(data.getTransportCertificateValidityEnd(), DateTimePatternEnum.Date));
                messageMap.put("CertifyDateB", formatDateTime(data.getFirstRegisterTime(), DateTimePatternEnum.Date));
                messageMap.put("FixState", data.getStateOfRepair());
                messageMap.put("CheckState", data.getAnnualAuditStatus());
                messageMap.put("FeePrintId", data.getInvoicePrintingEquipmentNumber());
                messageMap.put("GPSBrand", data.getGpsBrand());
                messageMap.put("GPSModel", data.getGpsModel());
                messageMap.put("GPSInstallDate", formatDateTime(data.getGpsInstallTime(), DateTimePatternEnum.Date));
                messageMap.put("RegisterDate", formatDateTime(data.getReportTime(), DateTimePatternEnum.Date));
                messageMap.put("CommercialType", 1);
                messageMap.put("FareType", data.getChargeTypeCode());
                messageMap.put("State", data.getUseStatus());
                messageMap.put("Flag", flag);
                messageMap.put("UpdateTime", now());
                return true;
            } catch (Exception e) {
                if (p == maxTimes && data != null) {
                    log.error("数据上报异常：ipcType={}, data={}", ipcType.name(), data, e);
                }
                return false;
            }
        });
    }
}
