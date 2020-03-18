package com.online.taxi.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 部级平台接口请求报文基类 Created by wn on 2018/1/27.
 */
public abstract class BaseMPRequest {

    // 公司（/平台）标识，部平台统一分配网约车平台公司标识
    @JSONField(name = "CompanyId")
    protected String companyId = "3301YPZCX78Q";
    // 消息来源标识（部平台统一分配消息的数据链路来源标识）
    @JSONField(name = "Source")
    protected String source = "0";
    // 业务接口代码，具体见接口定义
    @JSONField(name = "IPCType")
    protected String ipcType;
    // 业务接口请求地址
    protected transient String requestUrl;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIpcType() {
        return ipcType;
    }

    public void setIpcType(String ipcType) {
        this.ipcType = ipcType;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public abstract void setRequestUrl(String requestUrl);

    public String toJson(JsonParser jsonParser) throws Exception {
        return jsonParser.object2Json(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
