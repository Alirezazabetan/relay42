package com.relay42.sensor.service.util;

 
public class StringUtil {

	public static boolean isNotEmpty(String input) {
		return (input != null && input.trim().length() > 0);
	}

	public static boolean isEmpty(String input) {
		if (input == null) {
			return true;
		}
		return input.trim().length() == 0;
	}

}