package com.online.taxi.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.annotation.JSONField;

public class VehicleRequest extends BaseMPRequest {

    @JSONField(name = "Address")
    private Integer address;

    @JSONField(name = "VehicleNo")
    private String vehicleNo;

    @JSONField(name = "PlateColor")
    private String plateColor;

    @JSONField(name = "Seats")
    private Integer seats;

    @JSONField(name = "Brand")
    private String brand;

    @JSONField(name = "Model")
    private String model;

    @JSONField(name = "VehicleType")
    private String vehicleType;

    @JSONField(name = "OwnerName")
    private String ownerName;

    @JSONField(name = "VehicleColor")
    private String vehicleColor;

    @JSONField(name = "EngineId")
    private String engineId;

    @JSONField(name = "VIN")
    private String vin;

    @JSONField(name = "CertifyDateA")
    private Long certifyDateA;

    @JSONField(name = "FuelType")
    private String fuelType;

    @JSONField(name = "EngineDisplace")
    private String engineDisplace;

    @JSONField(name = "TransAgency")
    private String transAgency;

    @JSONField(name = "TransArea")
    private String transArea;

    @JSONField(name = "TransDateStart")
    private Long transDateStart;

    @JSONField(name = "TransDateStop")
    private Long transDateStop;

    @JSONField(name = "CertifyDateB")
    private Long certifyDateB;

    @JSONField(name = "FixState")
    private String fixState;

    @JSONField(name = "CheckState")
    private String checkState;

    @JSONField(name = "FeePrintId")
    private String feePrintId;

    @JSONField(name = "GPSBrand")
    private String gpsBrand;

    @JSONField(name = "GPSModel")
    private String gpsModel;

    @JSONField(name = "GPSInstallDate")
    private Long gpsInstallDate;

    @JSONField(name = "RegisterDate")
    private Long registerDate;

    @JSONField(name = "CommercialType")
    private Integer commercialType;

    @JSONField(name = "FareType")
    private String fareType;

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

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getEngineId() {
        return engineId;
    }

    public void setEngineId(String engineId) {
        this.engineId = engineId;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Long getCertifyDateA() {
        return certifyDateA;
    }

    public void setCertifyDateA(Long certifyDateA) {
        this.certifyDateA = certifyDateA;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getEngineDisplace() {
        return engineDisplace;
    }

    public void setEngineDisplace(String engineDisplace) {
        this.engineDisplace = engineDisplace;
    }

    public String getTransAgency() {
        return transAgency;
    }

    public void setTransAgency(String transAgency) {
        this.transAgency = transAgency;
    }

    public String getTransArea() {
        return transArea;
    }

    public void setTransArea(String transArea) {
        this.transArea = transArea;
    }

    public Long getTransDateStart() {
        return transDateStart;
    }

    public void setTransDateStart(Long transDateStart) {
        this.transDateStart = transDateStart;
    }

    public Long getTransDateStop() {
        return transDateStop;
    }

    public void setTransDateStop(Long transDateStop) {
        this.transDateStop = transDateStop;
    }

    public Long getCertifyDateB() {
        return certifyDateB;
    }

    public void setCertifyDateB(Long certifyDateB) {
        this.certifyDateB = certifyDateB;
    }

    public String getFixState() {
        return fixState;
    }

    public void setFixState(String fixState) {
        this.fixState = fixState;
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getFeePrintId() {
        return feePrintId;
    }

    public void setFeePrintId(String feePrintId) {
        this.feePrintId = feePrintId;
    }

    public String getGpsBrand() {
        return gpsBrand;
    }

    public void setGpsBrand(String gpsBrand) {
        this.gpsBrand = gpsBrand;
    }

    public String getGpsModel() {
        return gpsModel;
    }

    public void setGpsModel(String gpsModel) {
        this.gpsModel = gpsModel;
    }

    public Long getGpsInstallDate() {
        return gpsInstallDate;
    }

    public void setGpsInstallDate(Long gpsInstallDate) {
        this.gpsInstallDate = gpsInstallDate;
    }

    public Long getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Long registerDate) {
        this.registerDate = registerDate;
    }

    public Integer getCommercialType() {
        return commercialType;
    }

    public void setCommercialType(Integer commercialType) {
        this.commercialType = commercialType;
    }

    public String getFareType() {
        return fareType;
    }

    public void setFareType(String fareType) {
        this.fareType = fareType;
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

    public VehicleRequest() {
    }

    public VehicleRequest(String ipcType, String requestUrl) {
        this.ipcType = ipcType;
        this.requestUrl = requestUrl;
    }

    @Override
    public void setRequestUrl(String requestUrl) {
        super.requestUrl = requestUrl;

    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
