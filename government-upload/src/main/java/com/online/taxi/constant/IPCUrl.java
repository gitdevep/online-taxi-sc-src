package com.online.taxi.constant;

/**
 * 部级平台业务接口URL
 * Created by wn on 2018/1/29.
 */
public enum IPCUrl {

    /**
     * 表A.4 网约车平台公司基本信息
     */
    BASEINFOCOMPANY("baseInfoCompany", "/baseinfo/company"),
    /**
     * 表A.6 网约车平台公司营运规模信息
     */
    BASEINFOCOMPANYSTAT("baseInfoCompanyStat", "/baseinfo/companystat"),
    /**
     * 表A.8 网约车平台公司支付信息
     */
    BASEINFOCOMPANYPAY("baseInfoCompanyPay", "/baseinfo/companypay"),
    /**
     * 表A.10 网约车平台公司服务机构 全表
     */
    BASEINFOCOMPANYSERVICE("baseInfoCompanyService", "/baseinfo/companyservice"),
    /**
     * 表A.12 网约车平台公司经营许可
     */
    BASEINFOCOMPANYPERMIT("baseInfoCompanyPermit", "/baseinfo/companypermit"),
    /**
     * 表A.14 网约车平台公司运价信息
     */
    BASEINFOCOMPANYFARE("baseInfoCompanyFare", "/baseinfo/companyfare"),
    /**
     * 表A.16 车辆基本信息
     */
    BASEINFOVEHICLE("baseInfoVehicle", "/baseinfo/vehicle"),
    /**
     * A.18 车辆保险信息
     */
    BASEINFOVEHICLEINSURANCE("baseInfoVehicleInsurance", "/baseinfo/vehicleinsurance"),
    /**
     * A.20 网约车车辆里程信息
     */
    BASEINFOVEHICLETOTALMILE("baseInfoVehicleTotalMile", "/baseinfo/vehicletotalmile"),
    /**
     * A.22 驾驶员基本信息
     */
    BASEINFODRIVER("baseInfoDriver", "/baseinfo/driver"),
    /**
     * A.24 网约车驾驶员培训信息
     */
    BASEINFODRIVEREDUCATE("baseInfoDriverEducate", "/baseinfo/drivereducate"),
    /**
     * A.26 驾驶员移动终端信息
     */
    BASEINFODRIVERAPP("baseInfoDriverApp", "/baseinfo/driverapp"),
    /**
     * A.28 网约车驾驶员统计信息
     */
    BASEINFODRIVERSTAT("baseInfoDriverStat", "/baseinfo/driverstat"),
    /**
     * A.30 乘客基本信息
     */
    BASEINFOPASSENGER("baseInfoPassenger", "/baseinfo/passenger"),
    /**
     * A.32 订单发起
     * A.34 订单成功
     * A.36 订单撤销
     */
    ORDERCREATE("orderCreate", "order/create"),
    ORDERMATCH("orderMatch", "order/match"),
    ORDERCANCEL("orderCancel", "order/cancel"),
    /**
     * A.38 车辆运营上线
     * A.40 车辆运营下线
     * A.42 经营出发
     * A.44 经营到达
     */
    OPERATELOGIN("operateLogin", "operate/login"),
    OPERATELOGOUT("operateLogout", "operate/logout"),
    OPERATEDEPART("operateDepart", "operate/depart"),
    OPERATEARRIVE("operateArrive", "operate/arrive"),
    /**
     * A.46 经营支付
     */
    OPERATEPAY("operatePay", "operate/pay"),
    /**
     * A.50 车辆定位信息
     */
    POSITIONDRIVER("positionDriver", "position/driver"),
    POSITIONVEHICLE("positionVehicle", "position/vehicle"),
    /**
     * A.52 乘客评价信息
     */
    RATEDPASSENGER("ratedPassenger", "rated/passenger"),
    /**
     * A.54 乘客投诉信息
     */
    RATEDPASSENGERCOMPLAINT("ratedPassengerComplaint", "rated/passengercomplaint"),
    /**
     * A.58 驾驶员信誉信息
     */
    RATEDDRIVERPUNISH("ratedDriverPunish", "rated/driverpunish"),
    RATEDDRIVER("ratedDriver", "rated/driver");

    private String key;

    private String value;

    IPCUrl(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static String getValue(String key) {
        if (key != null) {
            for (IPCUrl ipcUrl : values()) {
                if (key.equals(ipcUrl.getKey())) {
                    return ipcUrl.getValue();
                }
            }
        }
        return null;
    }

}