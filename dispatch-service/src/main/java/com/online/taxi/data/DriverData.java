package com.online.taxi.data;

import com.online.taxi.dto.map.Vehicle;
import com.online.taxi.entity.CarInfo;
import com.online.taxi.entity.DriverInfo;

import lombok.Data;

/**
 */
@Data
public class DriverData {
    private Vehicle amapVehicle;
    private DriverInfo driverInfo;
    private double homeDistance;
    private CarInfo carInfo;
    private int isFollowing;
    private int timeSort;
}
