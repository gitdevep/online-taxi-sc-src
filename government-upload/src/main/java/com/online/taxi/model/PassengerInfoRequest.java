package com.online.taxi.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @description 报部：乘客基本信息
 * @author jxl
 * @version
 * @date 2018年1月30日
 */
public class PassengerInfoRequest extends BaseMPRequest {

    @JSONField(name = "PassengerPhone")
    private String passengerPhone;

    @JSONField(name = "PassengerName")
    private String passengerName;

    @JSONField(name = "PassengerGender")
    private String passengerGender;

    @JSONField(name = "State")
    private Integer state;

    @JSONField(name = "Flag")
    private Integer flag;

    @JSONField(name = "UpdateTime")
    private Long updateTime;

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerGender() {
        return passengerGender;
    }

    public void setPassengerGender(String passengerGender) {
        this.passengerGender = passengerGender;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public PassengerInfoRequest(String ipcType, String requestUrl) {
        this.ipcType = ipcType;
        this.requestUrl = requestUrl;
    }

    @Override
    public void setRequestUrl(String requestUrl) {
        super.requestUrl = requestUrl;
    }

}
