package com.online.taxi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 
 */
@RestController
public class TestController {

	@RequestMapping("/test")
	public String test() {
		return "map success";
	}
}
