package com.online.taxi.response;

import java.util.ArrayList;
import java.util.List;

import com.online.taxi.entity.FenceEntity;

import lombok.Data;

@Data
public class AmapFenceSearchResponse {
	
	private List<FenceEntity> list;
	private int count;
	
	public AmapFenceSearchResponse(List<FenceEntity> list, int count) {
		this.list = list;
		this.count = count;
	}

	public AmapFenceSearchResponse() {
		
	}

}
