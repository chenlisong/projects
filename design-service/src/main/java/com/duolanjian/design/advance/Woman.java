package com.duolanjian.design.advance;

public class Woman implements IWoman{
	
	//级别，1未婚，2已婚，3丧夫
	private int type = 0;
	
	//请求内容
	private String request = "";
	
	public Woman(int _type, String _request) {
		this.type = _type;
		this.request = _request;
	}

	public int getType() {
		return type;
	}

	public String getRequest() {
		return request;
	}

}
