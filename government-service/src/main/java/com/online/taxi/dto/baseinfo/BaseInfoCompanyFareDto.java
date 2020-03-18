package com.online.taxi.dto.baseinfo;

import lombok.Data;

import java.util.Date;

/**
 * 网约车平台运价信息
 *
 * @date 2018/8/29
 */
@Data
public class BaseInfoCompanyFareDto {
    /**
     *
     */
    private Integer id;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 价格类型：0预估，1实际
     */
    private String category;

    /**
     * 总价
     */
    private Double totalPrice;

    /**
     * 总距离（公里）
     */
    private Double totalDistance;

    /**
     * 总时间（分钟）
     */
    private Double totalTime;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 服务类型id
     */
    private Integer serviceTypeId;

    /**
     * 服务类型名称
     */
    private String serviceTypeName;

    /**
     * 渠道id
     */
    private Integer channelId;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 车辆级别id
     */
    private Integer carLevelId;

    /**
     * 车辆级别名称
     */
    private String carLevelName;

    /**
     * 基础价格
     */
    private Double basePrice;

    /**
     * 基础价格包含公里数
     */
    private Double baseKilo;

    /**
     * 基础价格包含时长数（分钟）
     */
    private Double baseMinute;

    /**
     * 最低消费
     */
    private Double lowestPrice;

    /**
     * 夜间时间段开始
     */
    private Date nightStart;

    /**
     * 夜间时间段结束
     */
    private Date nightEnd;

    /**
     * 夜间超公里加收单价
     */
    private Double nightPerKiloPrice;

    /**
     * 夜间超时间加收单价
     */
    private Double nightPerMinutePrice;

    /**
     * 夜间行驶总里程（公里）
     */
    private Double nightDistance;

    /**
     * 夜间行驶总时间（分钟）
     */
    private Double nightTime;

    /**
     * 夜间服务费
     */
    private Double nightPrice;

    /**
     * 远途起算公里
     */
    private Double beyondStartKilo;

    /**
     * 远途单价
     */
    private Double beyondPerKiloPrice;

    /**
     * 远途距离，超出远途的公里数
     */
    private Double beyondDistance;

    /**
     * 远途费
     */
    private Double beyondPrice;

    /**
     * 超公里单价(每公里单价)
     */
    private Double perKiloPrice;

    /**
     * (不包含起步)行驶总里程（公里）或分段计价合计里程（公里）
     */
    private Double path;

    /**
     * (不包含起步)行驶总里程价格或分段计价合计价格
     */
    private Double pathPrice;

    /**
     * 超时间单价(每分钟单价)或分段计价默认的每分钟单价
     */
    private Double perMinutePrice;

    /**
     * (不包含起步)行驶时长（分钟）或分段计价合计时长（分钟）
     */
    private Double duration;

    /**
     * (不包含起步)行驶时长价格或分段计价合计时间价格
     */
    private Double durationPrice;

    /**
     * 过路费
     */
    private Double roadPrice;

    /**
     * 停车费
     */
    private Double parkingPrice;

    /**
     * 其它费用
     */
    private Double otherPrice;

    /**
     *
     */
    private Double cancelPrice;

    /**
     * 动态调价的折扣率(0-1 两小数)
     */
    private Double dynamicDiscountRate;

    /**
     * 动态调价的金额
     */
    private Double dynamicDiscount;

    /**
     * 最低消费补足的费用
     */
    private Double supplementPrice;

    /**
     * 生效时间
     */
    private Date effectiveTime;

}
