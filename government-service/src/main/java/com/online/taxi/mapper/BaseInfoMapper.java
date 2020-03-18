package com.online.taxi.mapper;

import com.online.taxi.dto.baseinfo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 基础信息Mapper
 *
 * @date 2018/8/24
 */

@Mapper
public interface BaseInfoMapper {

    /**
     * 获取司机基本信息
     *
     * @param id 主键
     * @return 司机基本信息
     */
    BaseInfoDriverDto getBaseInfoDriver(Integer id);

    /**
     * 获取车辆基本信息
     *
     * @param id 主键
     * @return 车辆
     */
    BaseInfoVehicleDto getBaseInfoVehicle(Integer id);

    /**
     * 获取司机订单信息统计
     *
     * @param cycle
     * @return
     */
    List<DriverOrderMessageStatisticalDto> getDriverOrderMessageStatistical(String cycle);

    /**
     * 网约车车辆里程信息
     *
     * @param id 主键
     * @return 网约车车辆里程信息
     */
    BaseInfoVehicleTotalMileDto getVehicleTotalMile(Integer id);

    BaseInfoCompanyFareDto getBaseInfoCompanyFareById(Integer id);

    BaseInfoCompanyDto getBaseInfoCompanyDto(Integer id);

    BaseInfoCompanyServiceDto getBaseInfoCompanyServiceDto(Integer id);

    BaseInfoCompanyPermitDto getBaseInfoCompanyPermitDto(Integer id);

    BaseInfoCompanyStatDto getBaseInfoCompanyStatDto();

    BaseInfoCompanyPayDto getBaseInfoCompanyPayDto(Integer id);

    BaseInfoVehicleInsuranceDto getCarInsurance(Integer id);

    BaseInfoPassengerDto getPassenger(Integer id);
}
