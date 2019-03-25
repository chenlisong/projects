package com.duolanjian.design.advance;

public class Husband extends Handler {

	public Husband(int _type) {
		super(2);
	}

	@Override
	public void response(Woman woman) {
		System.out.println("请求得到丈夫的同意");
		System.out.println(woman.getRequest());
		System.out.println("丈夫：已同意\n");
	}

}
