package com.online.taxi.service.impl;

import com.online.taxi.constant.IPCUrl;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.model.VehicleRequest;
import com.online.taxi.service.YiPinService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 车辆基本信息上报
 * @author admin
 *
 */
@Service("baseInfoVehicleService")
public class BaseInfoVehicleService implements YiPinService {

    @Override
    public BaseMPRequest execute(Map<String,Object> message) throws Exception {
        VehicleRequest vehicleRequest = new VehicleRequest(IPCUrl.BASEINFOVEHICLE.getKey(), IPCUrl.BASEINFOVEHICLE.getValue());
        vehicleRequest.setAddress((Integer) message.get("Address"));
        vehicleRequest.setVehicleNo((String) message.get("VehicleNo"));
        vehicleRequest.setPlateColor((String) message.get("PlateColor"));
        vehicleRequest.setSeats((Integer) message.get("Seats"));
        vehicleRequest.setBrand((String) message.get("Brand"));
        vehicleRequest.setModel((String) message.get("Model"));
        vehicleRequest.setVehicleType((String) message.get("VehicleType"));
        vehicleRequest.setOwnerName((String) message.get("OwnerName"));
        vehicleRequest.setVehicleColor((String) message.get("VehicleColor"));
        vehicleRequest.setEngineId((String) message.get("EngineId"));
        vehicleRequest.setVin((String) message.get("VIN"));
        vehicleRequest.setCertifyDateA((Long) message.get("CertifyDateA"));
        vehicleRequest.setFuelType((String) message.get("FuelType"));
        vehicleRequest.setEngineDisplace((String) message.get("EngineDisplace"));
        vehicleRequest.setTransAgency((String) message.get("TransAgency"));
        vehicleRequest.setTransArea((String) message.get("TransArea"));
        vehicleRequest.setTransDateStart((Long) message.get("TransDateStart"));
        vehicleRequest.setTransDateStop((Long) message.get("TransDateStop"));
        vehicleRequest.setCertifyDateB((Long) message.get("CertifyDateB"));
        vehicleRequest.setFixState((String) message.get("FixState"));
        vehicleRequest.setCheckState((String) message.get("CheckState"));
        vehicleRequest.setFeePrintId((String) message.get("FeePrintId"));
        vehicleRequest.setGpsBrand((String) message.get("GPSBrand"));
        vehicleRequest.setGpsModel((String) message.get("GPSModel"));
        vehicleRequest.setGpsInstallDate((Long) message.get("GPSInstallDate"));
        vehicleRequest.setRegisterDate((Long) message.get("RegisterDate"));
        vehicleRequest.setCommercialType((Integer) message.get("CommercialType"));
        //vehicleRequest.setCompanyId((Integer) message.get("Address"));
        vehicleRequest.setFareType((String) message.get("FareType"));
        vehicleRequest.setState((Integer) message.get("State"));
        vehicleRequest.setFlag((Integer) message.get("Flag"));
        vehicleRequest.setUpdateTime((Long) message.get("UpdateTime"));

        return vehicleRequest;
    }

}
