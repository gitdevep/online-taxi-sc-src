package com.online.taxi.mapper;

import com.online.taxi.dto.baseinfo.*;
import com.online.taxi.dto.operate.OperateDto;
import com.online.taxi.dto.operate.OperatePayDto;
import com.online.taxi.dto.position.PositionDriverDto;
import com.online.taxi.dto.rated.RatedDriverDto;
import com.online.taxi.dto.rated.RatedDriverPunishDto;
import com.online.taxi.dto.rated.RatedPassengerDto;
import com.online.taxi.entity.BaseInfoCompany;
import com.online.taxi.entity.DriverOrderMessageStatistical;
import com.online.taxi.entity.Order;
import com.online.taxi.entity.PassengerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @date 2018/9/26
 */
@Mapper
public interface TestMapper {
    @Select("select * from tbl_base_info_company")
    List<BaseInfoCompany> selectBaseInfoCompany();

    @Select("select * from tbl_base_info_company_pay")
    List<BaseInfoCompanyPayDto> selectBaseInfoCompanyPay();

    @Select("select * from tbl_base_info_company_service")
    List<BaseInfoCompanyServiceDto> selectBaseInfoCompanyService();

    @Select("select * from tbl_base_info_company_permit")
    List<BaseInfoCompanyPermitDto> selectBaseInfoCompanyPermit();

    @Select("SELECT * FROM tbl_charge_rule")
    List<BaseInfoCompanyFareDto> selectBaseInfoCompanyFare();

    @Select("SELECT t1.id, t1.plate_number, t2.plate_color, t3.seats, t3.brand, t3.model, t2.car_base_type, t2.car_owner, t1.color, t2.engine_number, t2.vin_number, t2.register_time, t2.fuel_type, t2.engine_capacity, t2.transport_issuing_authority, t2.business_area, t2.transport_certificate_validity_start, t2.transport_certificate_validity_end, t2.first_register_time, t2.state_of_repair, t2.annual_audit_status, t2.invoice_printing_equipment_number, t2.gps_brand, t2.gps_model, t2.gps_install_time, t2.report_time, t2.service_type, t2.charge_type_code, t1.use_status FROM tbl_car_info t1 LEFT JOIN tbl_car_base_info t2 ON t1.id = t2.id LEFT JOIN tbl_car_type t3 ON t1.car_type_id = t3.id")
    List<BaseInfoVehicleDto> selectBaseInfoVehicle();

    @Select("select * from tbl_car_insurance")
    List<BaseInfoVehicleInsuranceDto> selectCarInsurance();

    @Select("select id,plate_number,total_mile from tbl_car_info")
    List<BaseInfoVehicleTotalMileDto> selectVehicleTotalMile();

    @Select("SELECT t.id, t.phone_number, t.gender, t1.birthday, t1.national, t1.address, t1.driving_licence_number, t1.first_get_driver_license_date, t1.driver_license_validity_start, t1.driver_license_validity_end, t1.is_taxi_driver, t1.network_reservation_taxi_driver_license_number, t1.network_reservation_taxi_driver_license_issuing_agencies, t1.certificate_issuing_date, t1.first_qualification_date, t1.qualification_certificate_validity_start, t1.qualification_certificate_validity_end, t1.reported_date, t1.service_type, t1.company, t1.contract_start_date, t1.contract_end_date, t1.training_courses, t1.training_courses_date, t1.training_courses_start_date, t1.training_courses_end_date, t1.training_courses_time FROM tbl_driver_info t JOIN tbl_driver_base_info t1 ON t.id = t1.id")
    List<BaseInfoDriverDto> selectBaseInfoDriver();

    @Select("SELECT * from tbl_driver_order_message_statistical")
    List<DriverOrderMessageStatistical> selectDriverOrderMessageStatistical();

    @Select("SELECT * from tbl_Passenger_Info")
    List<PassengerInfo> selectPassengerInfo();

    @Select("select * from tbl_order")
    List<Order> selectOrder();

    @Select("SELECT w.id, c.plate_number, b.driving_licence_number, w.work_start, w.work_end FROM tbl_driver_info d JOIN tbl_driver_base_info b ON d.id = b.id JOIN tbl_car_info c ON d.car_id = c.id JOIN tbl_driver_work_time w ON d.id = w.driver_id")
    List<OperateDto> selectDriverLoginLogout();

    @Select("SELECT t1.order_id, t2.order_number, t3.city_code, t4.driving_licence_number, t5.rule_id, t2.plate_number, t2.order_start_time, t2.receive_passenger_longitude, t2.receive_passenger_latitude, t2.receive_passenger_time, t2.passenger_getoff_longitude, t2.passenger_getoff_latitude, t2.passenger_getoff_time, t3.total_distance, t3.total_time, t1.total_price, t3.beyond_price, t2.invoice_type, t3.road_price, t3.parking_price, t3.other_price FROM `tbl_order_payment` t1 INNER JOIN tbl_order t2 ON t1.order_id = t2.id INNER JOIN tbl_order_rule_price t3 ON t2.id = t3.order_id INNER JOIN tbl_driver_base_info t4 ON t2.driver_id = t4.id INNER JOIN tbl_order_rule_mirror t5 ON t2.id = t5.order_id WHERE t3.category = 1 AND t2.status = 8")
    List<OperatePayDto> selectOperatorPay();

    @Select("SELECT t1.id, t1.car_id, t1.driver_id, t1.points, t2.plate_number, t3.city_code, t4.driving_licence_number, t5.order_number FROM `tbl_order_points` AS t1 JOIN tbl_car_info AS t2 ON t1.car_id = t2.id JOIN tbl_driver_info AS t3 ON t3.id = t1.driver_id JOIN tbl_driver_base_info AS t4 ON t3.id = t4.id JOIN tbl_order AS t5 ON t5.id = t1.order_id")
    List<PositionDriverDto> selectPosition();

    @Select("SELECT t2.id, t2.order_number, t1.update_time, t1.grade FROM tbl_evaluate_driver t1 INNER JOIN tbl_order t2 ON t1.order_id = t2.id")
    List<RatedPassengerDto> selectRatedPassenger();

    @Select("SELECT t1.id, t1.driver_id, t1.punish_time, t1.punish_result, t2.driving_licence_number FROM tbl_driver_punish t1 INNER JOIN tbl_driver_base_info t2 ON t1.driver_id = t2.id")
    List<RatedDriverPunishDto> selectRatedDriverPunish();

    @Select("SELECT t2.id, t2.driver_id, t1.driving_licence_number, t2.grade, t2.test_date, t2.test_department FROM tbl_driver_base_info t1 INNER JOIN tbl_driver_rate t2 ON t1.id = t2.driver_id")
    List<RatedDriverDto> selectRatedDriver();
}
