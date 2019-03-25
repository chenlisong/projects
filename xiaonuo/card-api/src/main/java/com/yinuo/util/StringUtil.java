package com.yinuo.util;

public class StringUtil {

	
	public static boolean isEmpty(String text) {
		
		if(text == null || text.equals("")) {
			return true;
		}
		return false;
	}	
	
	public static boolean isotEmpty(String text) {
		
		if(text == null || text.equals("")) {
			return false;
		}
		return true;
	}
	
}
