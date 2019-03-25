package com.duolanjian.design.decorator;

public class FourGradeReport extends SchoolReport{

	@Override
	public void report() {
		System.out.println("我考了60分。");
	}

	@Override
	public void sign(String name) {
		System.out.println("家长签字："+name);
	}

}
