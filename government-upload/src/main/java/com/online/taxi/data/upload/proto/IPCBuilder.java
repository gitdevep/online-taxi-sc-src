package com.online.taxi.data.upload.proto;

import org.apache.commons.lang.StringUtils;

/**
 * Created by wn on 2018/2/27.
 */
public enum IPCBuilder {

    BASEINFOCOMPANY("baseInfoCompany", OTIpcDef.BaseInfoCompany.newBuilder()),
    BASEINFOCOMPANYSTAT("baseInfoCompanyStat", OTIpcDef.BaseInfoCompanyStat.newBuilder()),
    BASEINFOCOMPANYPAY("baseInfoCompanyPay", OTIpcDef.BaseInfoCompanyPay.newBuilder()),
    BASEINFOCOMPANYSERVICE("baseInfoCompanyService", OTIpcDef.BaseInfoCompanyService.newBuilder()),
    BASEINFOCOMPANYPERMIT("baseInfoCompanyPermit", OTIpcDef.BaseInfoCompanyPermit.newBuilder()),
    BASEINFOCOMPANYFARE("baseInfoCompanyFare", OTIpcDef.BaseInfoCompanyFare.newBuilder()),
    BASEINFOVEHICLE("baseInfoVehicle", OTIpcDef.BaseInfoVehicle.newBuilder()),
    BASEINFOVEHICLEINSURANCE("baseInfoVehicleInsurance", OTIpcDef.BaseInfoVehicleInsurance.newBuilder()),
    BASEINFOVEHICLETOTALMILE("baseInfoVehicleTotalMile", OTIpcDef.BaseInfoVehicleTotalMile.newBuilder()),
    BASEINFODRIVER("baseInfoDriver", OTIpcDef.BaseInfoDriver.newBuilder()),
    BASEINFODRIVEREDUCATE("baseInfoDriverEducate", OTIpcDef.BaseInfoDriverEducate.newBuilder()),
    BASEINFODRIVERAPP("baseInfoDriverApp", OTIpcDef.BaseInfoDriverApp.newBuilder()),
    BASEINFODRIVERSTAT("baseInfoDriverStat", OTIpcDef.BaseInfoDriverStat.newBuilder()),
    BASEINFOPASSENGER("baseInfoPassenger", OTIpcDef.BaseInfoPassenger.newBuilder()),
    ORDERCREATE("orderCreate", OTIpcDef.OrderCreate.newBuilder()),
    ORDERMATCH("orderMatch", OTIpcDef.OrderMatch.newBuilder()),
    ORDERCANCEL("orderCancel", OTIpcDef.OrderCancel.newBuilder()),
    OPERATELOGIN("operateLogin", OTIpcDef.OperateLogin.newBuilder()),
    OPERATELOGOUT("operateLogout", OTIpcDef.OperateLogout.newBuilder()),
    OPERATEDEPART("operateDepart", OTIpcDef.OperateDepart.newBuilder()),
    OPERATEARRIVE("operateArrive", OTIpcDef.OperateArrive.newBuilder()),
    OPERATEPAY("operatePay", OTIpcDef.OperatePay.newBuilder()),
    POSITIONDRIVER("positionDriver", OTIpcDef.PositionDriver.newBuilder()),
    POSITIONVEHICLE("positionVehicle", OTIpcDef.PositionVehicle.newBuilder()),
    RATEDPASSENGER("ratedPassenger", OTIpcDef.RatedPassenger.newBuilder()),
    RATEDPASSENGERCOMPLAINT("ratedPassengerComplaint", OTIpcDef.RatedPassengerComplaint.newBuilder()),
    RATEDDRIVERPUNISH("ratedDriverPunish", OTIpcDef.RatedDriverPunish.newBuilder()),
    RATEDDRIVER("ratedDriver", OTIpcDef.RatedDriver.newBuilder());

    private String key;

    private com.google.protobuf.Message.Builder value;

    IPCBuilder(String key, com.google.protobuf.Message.Builder value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public com.google.protobuf.Message.Builder getValue() {
        return value;
    }

    public static com.google.protobuf.Message.Builder getValue(String key) {
        if (key != null) {
            for (IPCBuilder ipcType : values()) {
                if (key.equals(ipcType.getKey())) {
                    return ipcType.getValue();
                }
            }
        }
        return null;
    }

    public static IPCBuilder getIPCBuilderByKey(String key){
        for(IPCBuilder ipcBuilder : IPCBuilder.values()){
            if(StringUtils.equals(key, ipcBuilder.getKey())){
                return ipcBuilder;
            }
        }
        return null;
    }

}
