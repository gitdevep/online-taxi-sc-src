package com.online.taxi.dto.baseinfo;

import lombok.Data;

/**
 * 司机订单及其它信息统计
 *
 * @date 2018/9/11
 */
@Data
public class DriverOrderMessageStatisticalDto {

    /**
     * 司机Id
     */
    private Integer driverId;

    /**
     * 机动车驾驶证编号
     */
    private String drivingLicenceNumber;

    /**
     * 统计周期
     */
    private String cycle;

    /**
     * 完成订单次数
     */
    private Integer orderCount;

    /**
     * 交通违章次数
     */
    private Integer trafficViolationsCount;

    /**
     * 被投诉次数
     */
    private Integer complainedCount;
}
