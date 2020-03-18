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
 * 驾驶员基本信息
 *
 * @date 2018/8/29
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class BaseInfoDriverTask extends AbstractSupervisionTask {

    @NonNull
    private BaseInfoMapper baseInfoDriverMapper;

    /**
     * 监听到插入操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean insert(Integer id) {
        return execute(id, 1);
    }

    /**
     * 监听到更新操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean update(Integer id) {
        return execute(id, 2);
    }

    /**
     * 监听到删除操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean delete(Integer id) {
        return false;
    }

    private boolean execute(Integer id, int flag) {

        return tryComposeData(maxTimes, p -> {
            BaseInfoDriverDto data = null;
            ipcType = OTIpcDef.IpcType.baseInfoDriver;
            try {
                data = baseInfoDriverMapper.getBaseInfoDriver(id);
                messageMap.put("DriverPhone", getPhoneNumber(data.getPhoneNumber()));
                messageMap.put("DriverGender", data.getGender());
                messageMap.put("DriverBirthday", formatDateTime(data.getBirthday(), DateTimePatternEnum.Date));
                messageMap.put("DriverNation", data.getNational());
                messageMap.put("DriverContactAddress", data.getAddress());
                messageMap.put("LicenseId", data.getDrivingLicenceNumber());
                messageMap.put("GetDriverLicenseDate", formatDateTime(data.getFirstGetDriverLicenseDate(), DateTimePatternEnum.Date));
                messageMap.put("DriverLicenseOn", formatDateTime(data.getDriverLicenseValidityStart(), DateTimePatternEnum.Date));
                messageMap.put("DriverLicenseOff", formatDateTime(data.getDriverLicenseValidityEnd(), DateTimePatternEnum.Date));
                messageMap.put("TaxiDriver", data.getIsTaxiDriver());
                messageMap.put("CertificateNo", data.getNetworkReservationTaxiDriverLicenseNumber());
                messageMap.put("NetworkCarIssueOrganization", data.getNetworkReservationTaxiDriverLicenseIssuingAgencies());
                messageMap.put("NetworkCarIssueDate", formatDateTime(data.getCertificateIssuingDate(), DateTimePatternEnum.Date));
                messageMap.put("GetNetworkCarProofDate", formatDateTime(data.getFirstQualificationDate(), DateTimePatternEnum.Date));
                messageMap.put("NetworkCarProofOn", formatDateTime(data.getQualificationCertificateValidityStart(), DateTimePatternEnum.Date));
                messageMap.put("NetworkCarProofOff", formatDateTime(data.getQualificationCertificateValidityEnd(), DateTimePatternEnum.Date));
                messageMap.put("RegisterDate", formatDateTime(data.getReportedDate(), DateTimePatternEnum.Date));
                messageMap.put("CommercialType", data.getServiceType());
                messageMap.put("ContractCompany", data.getCompany());
                messageMap.put("ContractOn", formatDateTime(data.getContractStartDate(), DateTimePatternEnum.Date));
                messageMap.put("ContractOff", formatDateTime(data.getContractEndDate(), DateTimePatternEnum.Date));
                messageMap.put("State", 0);
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
