package com.duolanjian.design.adapter;

public class Client {

	public static void main(String[] args) {
		
		System.out.println("inner system data...");
		//before adapter
		IUserInfo userInfo = new UserInfo();
		userInfo.getName();
		userInfo.getMobile();
		userInfo.getAddress();

		System.out.println();
		System.out.println("outer system data...");
		//after adapter
		userInfo = new UserAdapter();
		userInfo.getName();
		userInfo.getMobile();
		userInfo.getAddress();
		
	}
	
}
