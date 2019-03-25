package com.duolanjian.design.model;

public class Client {

	public static void main(String[] args) {
		CarModel car = new CarH1();
		car.run();
		System.out.println();
		car = new CarH2();
		car.run();
		
	}
	
}
