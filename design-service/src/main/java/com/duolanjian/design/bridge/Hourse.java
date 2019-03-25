package com.duolanjian.design.bridge;

public class Hourse extends Product{

	@Override
	public void beProduced() {
		System.out.println("hourse produced ...");
	}

	@Override
	public void beSelled() {
		System.out.println("hourse selled ...");
	}

}
