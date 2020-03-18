package com.online.taxi.mapper;

import com.online.taxi.dto.position.PositionDriverDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * 位置Mapper
 *
 * @date 2018/9/14
 */
@Mapper
public interface PositionMapper {

    /**
     * 查找车辆轨迹
     *
     * @param id 车辆轨迹表主键
     * @return 驾驶员定位信息
     */
    PositionDriverDto selectPositionDriverById(Integer id);
}
