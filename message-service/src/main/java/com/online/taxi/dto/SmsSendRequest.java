package com.online.taxi.dto;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 */
@Data
public class SmsSendRequest{

	private String[] receivers;
	private List<SmsTemplateDto> data;

	@Override
	public String toString() {
		return "SmsSendRequest [reveivers=" + Arrays.toString(receivers) + ", data=" + data + "]";
	}

}