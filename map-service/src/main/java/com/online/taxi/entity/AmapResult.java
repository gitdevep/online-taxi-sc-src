package com.online.taxi.entity;

import lombok.Data;

/**
 * 
 * @param <T>
 */
@Data
public class AmapResult<T> {

	private int status;
	
	private T data;
	
	
}
