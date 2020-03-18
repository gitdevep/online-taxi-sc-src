package com.online.taxi.service;

import com.online.taxi.dto.map.request.OrderRequest;

/**
 * 
 */
public interface OrderService {

	/**
	 * 同步订单
	 * @param orderRequest
	 * @return
	 */
	public String order(OrderRequest orderRequest) ;
}
