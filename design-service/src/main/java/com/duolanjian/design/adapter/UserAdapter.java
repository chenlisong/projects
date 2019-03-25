package com.duolanjian.design.adapter;

import java.util.Map;

public class UserAdapter extends OuterUser implements IUserInfo{

	private Map<String,String> baseInfo = super.getBaseInfo();
	
	private Map<String,String> homeInfo = super.getHomeInfo();
	
	public String getName() {
		System.out.println(baseInfo.get("name"));
		return null;
	}

	public String getMobile() {
		System.out.println(baseInfo.get("mobile"));
		return null;
	}

	public String getAddress() {
		System.out.println(homeInfo.get("address"));
		return null;
	}

	
	
}
