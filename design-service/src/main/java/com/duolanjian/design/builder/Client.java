package com.duolanjian.design.builder;


public class Client {

	public static void main(String[] args) {

		Director director = new Director();
		director.getCar1AModel().run();
		System.out.println();
		director.getCar1BModel().run();
		System.out.println();
		director.getCar2AModel().run();
		System.out.println();
		director.getCar2BModel().run();
		
	}
}
