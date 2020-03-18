package com.online.taxi.dto.rated;

import lombok.Data;

import java.util.Date;

/**
 * 驾驶员处罚信息DTO
 *
 * @date 2018/9/1
 */
@Data
public class RatedDriverPunishDto {

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
     * 处罚时间
     */
    private Date punishTime;

    /**
     * 处罚结果
     */
    private String punishResult;
}
