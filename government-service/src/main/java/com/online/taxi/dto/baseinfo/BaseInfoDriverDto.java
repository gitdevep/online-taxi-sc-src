package com.online.taxi.dto.baseinfo;

import lombok.Data;

import java.util.Date;

/**
 * 司机基本信息
 *
 * @date 2018/9/1
 */
@Data
public class BaseInfoDriverDto {
    /**
     * PK
     */
    private Integer id;

    /**
     * 手机运营商
     */
    private Integer mobileOperator;

    /**
     * 司机手机号
     */
    private String phoneNumber;

    /**
     * 司机性别
     */
    private String gender;

    /**
     * 司机出生日期
     */
    private Date birthday;

    /**
     * 司机民族
     */
    private String national;

    /**
     * 司机通讯地址
     */
    private String address;

    /**
     * 司机驾驶证编号
     */
    private String drivingLicenceNumber;

    /**
     * 司机初次领取驾驶证日期
     */
    private Date firstGetDriverLicenseDate;

    /**
     * 司机驾驶证有效期起
     */
    private Date driverLicenseValidityStart;

    /**
     * 司机驾驶证有效期止
     */
    private Date driverLicenseValidityEnd;

    /**
     * 是否巡游出租车驾驶员
     */
    private Integer isTaxiDriver;

    /**
     * 网络预约出租汽车驾驶员证号
     */
    private String networkReservationTaxiDriverLicenseNumber;

    /**
     * 网络预约出租汽车驾驶员证发证机构
     */
    private String networkReservationTaxiDriverLicenseIssuingAgencies;

    /**
     * 资格证发证日期
     */
    private Date certificateIssuingDate;

    /**
     * 初次领取资格证日期
     */
    private Date firstQualificationDate;

    /**
     * 资格证有效期起
     */
    private Date qualificationCertificateValidityStart;

    /**
     * 资格证有效期止
     */
    private Date qualificationCertificateValidityEnd;

    /**
     * 报备日期
     */
    private Date reportedDate;

    /**
     * 服务类型
     */
    private Integer serviceType;

    /**
     * 驾驶员合同（或协议）签署公司
     */
    private String company;

    /**
     * 合同(或协议)有效期起
     */
    private Date contractStartDate;

    /**
     * 合同(或协议)有效期止
     */
    private Date contractEndDate;

    /**
     * 培训课名称
     */
    private String trainingCourses;

    /**
     * 培训课程日期
     */
    private Date trainingCoursesDate;

    /**
     * 培训开始日期
     */
    private Date trainingCoursesStartDate;

    /**
     * 培训结束日期
     */
    private Date trainingCoursesEndDate;

    /**
     *
     */
    private Integer trainingCoursesTime;

    /**
     * APP版本号
     */
    private String appVersion;

}
