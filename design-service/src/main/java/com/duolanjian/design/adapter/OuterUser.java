package com.duolanjian.design.adapter;

import java.util.HashMap;
import java.util.Map;

public class OuterUser implements IOuterUser{

	public Map<String, String> getBaseInfo() {
		Map<String,String> baseInfo = new HashMap<String, String>();
		baseInfo.put("name", "外部系统张三");
		baseInfo.put("mobile", "2222");
		return baseInfo;
	}

	public Map<String, String> getHomeInfo() {
		Map<String,String> homeInfo = new HashMap<String, String>();
		homeInfo.put("address", "城南外");
		return homeInfo;
	}

}
