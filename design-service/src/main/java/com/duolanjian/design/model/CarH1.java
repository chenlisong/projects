package com.duolanjian.design.model;

public class CarH1 extends CarModel{

	@Override
	public void start() {
		System.out.println("h1 start...");
	}

	@Override
	public void enginBoom() {
		System.out.println("h1 enginBoom...");
	}

	@Override
	public void alarm() {
		System.out.println("h1 alarm...");
	}

	@Override
	public void stop() {
		System.out.println("h1 stop...");
	}

}
