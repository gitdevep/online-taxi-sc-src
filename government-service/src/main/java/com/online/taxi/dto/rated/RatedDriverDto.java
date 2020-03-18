package com.online.taxi.dto.rated;

import lombok.Data;

import java.util.Date;

/**
 * 驾驶员信誉信息DTO
 *
 * @date 2018/9/1
 */
@Data
public class RatedDriverDto {
    /**
     * PK
     */
    private Integer id;

    /**
     * 司机主键
     */
    private Integer driverId;

    /**
     * 机动车驾驶证编号
     */
    private String drivingLicenceNumber;

    /**
     * 服务质量信誉等级
     */
    private Integer grade;

    /**
     * 考核日期
     */
    private Date testDate;

    /**
     * 考核机构
     */
    private String testDepartment;

}
