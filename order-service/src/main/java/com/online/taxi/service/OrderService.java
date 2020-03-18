package com.online.taxi.service;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.entity.Order;
import com.online.taxi.dto.OrderOtherPrice;
import com.online.taxi.dto.OrderPrice;
import com.online.taxi.request.OrderDtoRequest;

/**
 * 功能描述
 *
 * @date 2018/8/25
 */
public interface OrderService {

    /**
     * 订单预估
     * @param request
     * @return
     * @throws Exception
     */
    ResponseResult<OrderPrice> forecastOrderCreate(OrderDtoRequest request) throws Exception;

    /**
     * 叫车
     * @param request
     * @return
     * @throws Exception
     */
    ResponseResult callCar(OrderDtoRequest request) throws Exception;

    /**
     * 订单修改
     * @param orderDtoRequest
     * @param driverLongitude
     * @param driverLatitude
     * @return
     */
    ResponseResult updateOrder(OrderDtoRequest orderDtoRequest,String driverLongitude,String driverLatitude);

    /**
     * 其它费用结算
     * @param request
     * @return
     * @throws Exception
     */
    ResponseResult<OrderOtherPrice> otherPriceBalance(OrderDtoRequest request) throws Exception;

    /**
     * 订单改派
     * @param request
     * @return
     * @throws Exception
     */
    ResponseResult reassignmentOrder(OrderDtoRequest request) throws Exception;

    /**
     * 批量修改
     * @param request
     * @return
     * @throws Exception
     */
    ResponseResult batchUpdate(OrderDtoRequest request) throws Exception;

}
