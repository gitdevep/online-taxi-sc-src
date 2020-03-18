package com.online.taxi.util;
/**
 * 
 */
public class AmapLocationUtils {
	
	public static String getLongitude(String location) {
		String[] locationArray = location.split(",");
		return locationArray[0];
	}
	
	public static String getLatitude(String location) {
		String[] locationArray = location.split(",");
		return locationArray[1];
	}
}
