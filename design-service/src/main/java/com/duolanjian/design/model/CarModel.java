package com.duolanjian.design.model;

public abstract class CarModel {

	public abstract void start();

	public abstract void enginBoom();

	public abstract void alarm();

	public abstract void stop();

	public void run() {
		this.start();
		this.enginBoom();
		this.alarm();
		this.stop();
	}
	
}
