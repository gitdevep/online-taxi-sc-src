package com.online.taxi.model;

import java.io.Serializable;

public class SpecificMessage implements Serializable {

    /**
     * 操作标识 1 更新 2 新增 3 删除
     */
    private int flag;

    /**
     * 业务接口代码
     */
    private String ipcType;

    /**
     * 主键的值
     */
    private String indexValue;

    public SpecificMessage(int flag, String ipcType, String indexValue) {
        this.flag = flag;
        this.ipcType = ipcType;
        this.indexValue = indexValue;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getIpcType() {
        return ipcType;
    }

    public void setIpcType(String ipcType) {
        this.ipcType = ipcType;
    }

    public String getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(String indexValue) {
        this.indexValue = indexValue;
    }

}
