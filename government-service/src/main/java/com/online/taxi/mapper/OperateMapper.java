package com.online.taxi.mapper;

import com.online.taxi.dto.operate.OperateDto;
import com.online.taxi.dto.operate.OperatePayDto;

/**
 * 经营Mapper
 *
 * @date 2018/8/30
 */
public interface OperateMapper {

    /**
     * 经营上线
     *
     * @param id
     * @return
     */
    OperateDto selectDriverLoginLogout(Integer id);

    /**
     * 经营出发
     *
     * @param id 订单主键
     * @return 经营DTO
     */
    OperateDto selectDeparture(Integer id);

    /**
     * 经营到达
     *
     * @param id 订单主键
     * @return 经营DTO
     */
    OperateDto selectArrival(Integer id);

    /**
     * 经营支付
     *
     * @param id 订单主键
     * @return 经营支付DTO
     */
    OperatePayDto selectOperatorPay(Integer id);

}
