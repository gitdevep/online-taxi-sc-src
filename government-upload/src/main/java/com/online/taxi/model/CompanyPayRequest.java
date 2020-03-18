package com.online.taxi.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @description 报部：公司支付基础信息
 * @author jxl
 * @version
 * @date 2018年1月29日
 */
public class CompanyPayRequest extends BaseMPRequest {

    @JSONField(name = "PayName")
    private String payName;

    @JSONField(name = "PayId")
    private String payId;

    @JSONField(name = "PayType")
    private String payType;

    @JSONField(name = "PayScope")
    private String payScope;

    @JSONField(name = "PrepareBank")
    private String prepareBank;

    @JSONField(name = "CountDate")
    private Integer countDate;

    @JSONField(name = "State")
    private Integer state;

    @JSONField(name = "Flag")
    private Integer flag;

    @JSONField(name = "UpdateTime")
    private Long updateTime;

    public CompanyPayRequest(String ipcType, String requestUrl) {
        this.ipcType = ipcType;
        this.requestUrl = requestUrl;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayScope() {
        return payScope;
    }

    public void setPayScope(String payScope) {
        this.payScope = payScope;
    }

    public String getPrepareBank() {
        return prepareBank;
    }

    public void setPrepareBank(String prepareBank) {
        this.prepareBank = prepareBank;
    }

    public Integer getCountDate() {
        return countDate;
    }

    public void setCountDate(Integer countDate) {
        this.countDate = countDate;
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

    @Override
    public void setRequestUrl(String requestUrl) {
        super.requestUrl = requestUrl;
    }

}
