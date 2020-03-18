package com.online.taxi.model;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @description 报部：司机培训课程信息
 * @author jxl
 * @version
 * @date 2018年1月30日
 */
@Service
public class DriverEducateRequest extends BaseMPRequest {

    @JSONField(name = "Address")
    private Integer address;

    @JSONField(name = "LicenseId")
    private String licenseId;

    @JSONField(name = "CourseName")
    private String courseName;

    @JSONField(name = "CourseDate")
    private Long courseDate;

    @JSONField(name = "StartTime")
    private String startTime;

    @JSONField(name = "StopTime")
    private String stopTime;

    @JSONField(name = "Duration")
    private Integer duration;

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

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(Long courseDate) {
        this.courseDate = courseDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    public DriverEducateRequest(String ipcType, String requestUrl) {
        this.ipcType = ipcType;
        this.requestUrl = requestUrl;
    }

    public DriverEducateRequest() {
    }

    @Override
    public void setRequestUrl(String requestUrl) {
        super.requestUrl = requestUrl;
    }
}
