package com.online.taxi.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 经营到达信息请求报文
 * Created by wn on 2018/1/29.
 */
public class OperateArriveRequest extends BaseMPRequest {

    @JSONField(name = "OrderId")
    private String orderId;

    @JSONField(name = "DestLongitude")
    private Long destLongitude;

    @JSONField(name = "DestLatitude")
    private Long destLatitude;

    @JSONField(name = "Encrypt")
    private Integer encrypt;

    @JSONField(name = "DestTime")
    private Long destTime;

    @JSONField(name = "DriveMile")
    private Long driveMile;

    @JSONField(name = "DriveTime")
    private Long driveTime;

    public OperateArriveRequest() {}

    public OperateArriveRequest(String ipcType, String requestUrl) {
        this.ipcType = ipcType;
        this.requestUrl = requestUrl;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getDestLongitude() {
        return destLongitude;
    }

    public void setDestLongitude(Long destLongitude) {
        this.destLongitude = destLongitude;
    }

    public Long getDestLatitude() {
        return destLatitude;
    }

    public void setDestLatitude(Long destLatitude) {
        this.destLatitude = destLatitude;
    }

    public Integer getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(Integer encrypt) {
        this.encrypt = encrypt;
    }

    public Long getDestTime() {
        return destTime;
    }

    public void setDestTime(Long destTime) {
        this.destTime = destTime;
    }

    public Long getDriveMile() {
        return driveMile;
    }

    public void setDriveMile(Long driveMile) {
        this.driveMile = driveMile;
    }

    public Long getDriveTime() {
        return driveTime;
    }

    public void setDriveTime(Long driveTime) {
        this.driveTime = driveTime;
    }

    public void setRequestUrl(String requestUrl) {
        super.requestUrl = requestUrl;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
