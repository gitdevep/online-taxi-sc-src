package com.online.taxi.controller;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.map.request.OrderRequest;
import com.online.taxi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 
 */
@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/order")
	public ResponseResult order(@RequestBody OrderRequest orderRequest){
		String result = orderService.order(orderRequest);
		return ResponseResult.success(result);
	}
}
