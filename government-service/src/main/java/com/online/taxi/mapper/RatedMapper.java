package com.online.taxi.mapper;

import com.online.taxi.dto.rated.RatedDriverDto;
import com.online.taxi.dto.rated.RatedDriverPunishDto;
import com.online.taxi.dto.rated.RatedPassengerComplaintDto;
import com.online.taxi.dto.rated.RatedPassengerDto;

import java.util.List;

/**
 * 评价信息Mapper
 *
 * @date 2018/9/1
 */
public interface RatedMapper {

    /**
     * 获取乘客评价信息
     *
     * @param id 主键
     * @return 乘客评价信息DTO
     */
    RatedPassengerDto selectRatedPassenger(Integer id);

    /**
     * 获取乘客投诉信息
     *
     * @param id 主键
     * @return 乘客投诉信息DTO
     */
    RatedPassengerComplaintDto selectRatedPassengerComplaint(Integer id);

    /**
     * 获取驾驶员信誉信息
     *
     * @return 驾驶员信誉信息DTO
     */
    List<RatedDriverDto> selectRatedDrivers();

    /**
     * 获取驾驶员信誉信息
     *
     * @return 驾驶员信誉信息DTO
     */
    RatedDriverDto selectRatedDriver(Integer id);

    /**
     * 获取驾驶员处罚信息
     *
     * @return 驾驶员处罚信息DTO
     */
    RatedDriverPunishDto selectRatedDriverPunish(Integer id);
}
