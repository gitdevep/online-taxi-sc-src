package com.online.taxi.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;



/**
 * 网约车平台公司运价信息请求报文 Created by zsw on 2018/1/29.
 */
public class BaseInfoCompanyFareRequest extends BaseMPRequest {
    @JSONField(name = "Address")
    private Integer address;

    @JSONField(name = "FareType")
    private String fareType;

    @JSONField(name = "FareValidOn")
    private Long fareValidOn;

    @JSONField(name = "StartFare")
    private Integer startFare;

    @JSONField(name = "StartMile")
    private Integer startMile;

    @JSONField(name = "UnitPricePerMile")
    private Double unitPricePerMile;

    @JSONField(name = "UnitPricePerMinute")
    private Double unitPricePerMinute;

    @JSONField(name = "MorningPeakTimeOn")
    private String morningPeakTimeOn;

    @JSONField(name = "MorningPeakTimeOff")
    private String morningPeakTimeOff;

    @JSONField(name = "EveningPeakTimeOn")
    private String eveningPeakTimeOn;

    @JSONField(name = "EveningPeakTimeOff")
    private String eveningPeakTimeOff;

    @JSONField(name = "PeakUnitPrice")
    private Integer peakUnitPrice;

    @JSONField(name = "PeakPriceStartMile")
    private Integer peakPriceStartMile;

    @JSONField(name = "State")
    private Integer state;

    @JSONField(name = "Flag")
    private Integer flag;

    @JSONField(name = "UpdateTime")
    private Long updateTime;

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public String getFareType() {
        return fareType;
    }

    public void setFareType(String fareType) {
        this.fareType = fareType;
    }

    public Long getFareValidOn() {
        return fareValidOn;
    }

    public void setFareValidOn(Long fareValidOn) {
        this.fareValidOn = fareValidOn;
    }

    public Integer getStartFare() {
        return startFare;
    }

    public void setStartFare(Integer startFare) {
        this.startFare = startFare;
    }

    public Integer getStartMile() {
        return startMile;
    }

    public void setStartMile(Integer startMile) {
        this.startMile = startMile;
    }

    public Double getUnitPricePerMile() {
        return unitPricePerMile;
    }

    public void setUnitPricePerMile(Double unitPricePerMile) {
        this.unitPricePerMile = unitPricePerMile;
    }

    public Double getUnitPricePerMinute() {
        return unitPricePerMinute;
    }

    public void setUnitPricePerMinute(Double unitPricePerMinute) {
        this.unitPricePerMinute = unitPricePerMinute;
    }

    public String getMorningPeakTimeOn() {
        return morningPeakTimeOn;
    }

    public void setMorningPeakTimeOn(String morningPeakTimeOn) {
        this.morningPeakTimeOn = morningPeakTimeOn;
    }

    public String getMorningPeakTimeOff() {
        return morningPeakTimeOff;
    }

    public void setMorningPeakTimeOff(String morningPeakTimeOff) {
        this.morningPeakTimeOff = morningPeakTimeOff;
    }

    public String getEveningPeakTimeOn() {
        return eveningPeakTimeOn;
    }

    public void setEveningPeakTimeOn(String eveningPeakTimeOn) {
        this.eveningPeakTimeOn = eveningPeakTimeOn;
    }

    public String getEveningPeakTimeOff() {
        return eveningPeakTimeOff;
    }

    public void setEveningPeakTimeOff(String eveningPeakTimeOff) {
        this.eveningPeakTimeOff = eveningPeakTimeOff;
    }

    public Integer getPeakUnitPrice() {
        return peakUnitPrice;
    }

    public void setPeakUnitPrice(Integer peakUnitPrice) {
        this.peakUnitPrice = peakUnitPrice;
    }

    public Integer getPeakPriceStartMile() {
        return peakPriceStartMile;
    }

    public void setPeakPriceStartMile(Integer peakPriceStartMile) {
        this.peakPriceStartMile = peakPriceStartMile;
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

    public BaseInfoCompanyFareRequest() {
    }

    public BaseInfoCompanyFareRequest(String ipcType, String requestUrl) {
        this.ipcType = ipcType;
        this.requestUrl = requestUrl;
    }

    @Override
    public void setRequestUrl(String requestUrl) {
        super.requestUrl = requestUrl;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
