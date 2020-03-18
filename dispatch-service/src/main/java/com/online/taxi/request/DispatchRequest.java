package com.online.taxi.request;

import lombok.Data;

@Data
public class DispatchRequest {

    private String orderId;

    private String customerDeviceId;

    private Integer orderType;

    private Integer vehicleType;

    private String orderCity;

    private Long orderTime;

    private Long startTime;

    private String startName;

    private String startLongitude;

    private String startLatitude;

    private String endName;

    private String endLongitude;

    private String endLatitude;

    private Integer radius;

    private Integer maxCount;

    public DispatchRequest() {

    }

    public DispatchRequest(String orderId, String customerDeviceId, Integer orderType, Integer vehicleType, String orderCity, Long orderTime, Long startTime, String startName, String startLongitude, String startLatitude, String endName, String endLongitude, String endLatitude, Integer radius, Integer maxCount) {
        this.orderId = orderId;
        this.customerDeviceId = customerDeviceId;
        this.orderType = orderType;
        this.vehicleType = vehicleType;
        this.orderCity = orderCity;
        this.orderTime = orderTime;
        this.startTime = startTime;
        this.startName = startName;
        this.startLongitude = startLongitude;
        this.startLatitude = startLatitude;
        this.endName = endName;
        this.endLongitude = endLongitude;
        this.endLatitude = endLatitude;
        this.radius = radius;
        this.maxCount = maxCount;
    }
}
