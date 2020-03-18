package com.online.taxi.response;

import lombok.Data;
import net.sf.json.JSONObject;
/**
 * 
 */
@Data
public class AmapHttpResponse {
	
	private int status;
	
	private JSONObject result;
}
