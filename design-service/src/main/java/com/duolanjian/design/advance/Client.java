package com.duolanjian.design.advance;

import java.util.Random;

public class Client {

	public static void main(String[] args) {
		Handler father = new Father(1);
		Handler husband = new Husband(2);
		Handler son = new Son(3);
		
		father.setNext(husband);
		husband.setNext(son);
		
		for(int i=0; i<5; i++) {
			int a = new Random().nextInt(3) + 1;
			Woman woman = new Woman(a, "我想去逛街");
			father.handleMessage(woman);
		}
		
	}
}
