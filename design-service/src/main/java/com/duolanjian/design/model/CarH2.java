package com.duolanjian.design.model;

public class CarH2 extends CarModel{

	@Override
	public void start() {
		System.out.println("h2 start...");
	}

	@Override
	public void enginBoom() {
		System.out.println("h2 enginBoom...");
	}

	@Override
	public void alarm() {
		System.out.println("h2 alarm...");
	}

	@Override
	public void stop() {
		System.out.println("h2 stop...");
	}

}
