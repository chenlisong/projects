package com.duolanjian.design.bridge;

public class Ipod extends Product{

	@Override
	public void beProduced() {
		System.out.println("ipod produced ...");
	}

	@Override
	public void beSelled() {
		System.out.println("ipod selled ...");
	}

}
