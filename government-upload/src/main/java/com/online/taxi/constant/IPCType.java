package com.online.taxi.constant;

import org.apache.commons.lang.StringUtils;

/**
 * 部级平台业务接口代码
 * @date 2018/10/16
 */
public enum IPCType {
    /**
     * 表A.4 网约车平台公司基本信息
     */
    BASEINFOCOMPANY("baseInfoCompany", "baseInfoCompanyService"),
    /**
     * 表A.6 网约车平台公司营运规模信息
     */
    BASEINFOCOMPANYSTAT("baseInfoCompanyStat", "baseInfoCompanyStatService"),
    /**
     * 表A.8 网约车平台公司支付信息
     */
    BASEINFOCOMPANYPAY("baseInfoCompanyPay", "baseInfoCompanyPayService"),
    /**
     * 表A.10 网约车平台公司服务机构 全表
     */
    BASEINFOCOMPANYSERVICE("baseInfoCompanyService", "baseInfoCompanyServiceService"),
    /**
     * 表A.12 网约车平台公司经营许可
     */
    BASEINFOCOMPANYPERMIT("baseInfoCompanyPermit", "baseInfoCompanyPermitService"),
    /**
     * 表A.14 网约车平台公司运价信息
     */
    BASEINFOCOMPANYFARE("baseInfoCompanyFare", "baseInfoCompanyFareService"),
    /**
     * 表A.16 车辆基本信息
     */
    BASEINFOVEHICLE("baseInfoVehicle", "baseInfoVehicleService"),
    /**
     * A.18 车辆保险信息
     */
    BASEINFOVEHICLEINSURANCE("baseInfoVehicleInsurance", "baseInfoVehicleInsuranceService"),
    /**
     * A.20 网约车车辆里程信息
     */
    BASEINFOVEHICLETOTALMILE("baseInfoVehicleTotalMile", "baseInfoVehicleTotalMileService"),
    /**
     * A.22 驾驶员基本信息
     */
    BASEINFODRIVER("baseInfoDriver", "baseInfoDriverService"),
    /**
     * A.24 网约车驾驶员培训信息
     */
    BASEINFODRIVEREDUCATE("baseInfoDriverEducate", "baseInfoDriverEducateService"),
    /**
     * A.26 驾驶员移动终端信息
     */
    BASEINFODRIVERAPP("baseInfoDriverApp", "baseInfoDriverAppService"),
    /**
     * A.28 网约车驾驶员统计信息
     */
    BASEINFODRIVERSTAT("baseInfoDriverStat", "baseInfoDriverStatService"),
    /**
     * A.30 乘客基本信息
     */
    BASEINFOPASSENGER("baseInfoPassenger", "baseInfoPassengerService"),
    /**
     * A.32 订单发起
     * A.34 订单成功
     * A.36 订单撤销
     */
    ORDERCREATE("orderCreate", "orderCreateService"),
    ORDERMATCH("orderMatch", "orderMatchService"),
    ORDERCANCEL("orderCancel", "orderCancelService"),
    /**
     * A.38 车辆运营上线
     * A.40 车辆运营下线
     * A.42 经营出发
     * A.44 经营到达
     */
    OPERATELOGIN("operateLogin", "operateLoginService"),
    OPERATELOGOUT("operateLogout", "operateLogoutService"),
    OPERATEDEPART("operateDepart", "operateDepartService"),
    OPERATEARRIVE("operateArrive", "operateArriveService"),
    /**
     * A.46 经营支付
     */
    OPERATEPAY("operatePay", "operatePayService"),
    /**
     * A.50 车辆定位信息
     */
    POSITIONDRIVER("positionDriver", "positionDriverService"),
    /**
     * A.50 车辆定位信息
     */
    POSITIONVEHICLE("positionVehicle", "positionVehicleService"),
    /**
     * A.52 乘客评价信息
     */
    RATEDPASSENGER("ratedPassenger", "ratedPassengerService"),
    /**
     * A.54 乘客投诉信息
     */
    RATEDPASSENGERCOMPLAINT("ratedPassengerComplaint", "ratedPassengerComplaintService"),

    /**
     * A.58 驾驶员信誉信息
     */
    RATEDDRIVERPUNISH("ratedDriverPunish", "ratedDriverPunishService"),
    RATEDDRIVER("ratedDriver", "ratedDriverService");

    private String key;

    private String value;

    IPCType(String key, String value) {
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
            for (IPCType ipcType : values()) {
                if (key.equals(ipcType.getKey())) {
                    return ipcType.getValue();
                }
            }
        }
        return null;
    }

    public static IPCType getIPCTypeByKey(String key){
        for(IPCType ipcType : IPCType.values()){
            if(StringUtils.equals(key, ipcType.getKey())){
                return ipcType;
            }
        }
        return null;
    }

}
