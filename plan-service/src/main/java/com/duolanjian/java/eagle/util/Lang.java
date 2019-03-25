package com.duolanjian.java.eagle.util;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class Lang extends HashMap<String, String> {
	
	private static final long serialVersionUID = 1L;

	public Lang() {
		put("NEED_LOGIN", "需要登录");
		put("INVALID_PARAM", "参数错误");
		put("INTERNAL_EXCEPTION", "内部错误");
	}
	
	public String getLang(String key) {
	    if (key == null) return "";
		String[] keyArray = key.split("\\$");
		String desc = get(keyArray[0]);
		if (desc == null) {
			return key;
		}
		
		int i = 0;
		for (String s :keyArray) {
			if (i ++ == 0) continue;
			desc = desc.replaceFirst("%s", s);
		}
		return desc;
	}

}
