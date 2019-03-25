package com.duolanjian.design.strategy;

public class Context {

	private IStrategy strategy;
	
	//构造要使用的妙计
	public Context (IStrategy strategy) {
		this.strategy = strategy;
	}
	
	//使用妙计，获得胜利
	public void operate() {
		this.strategy.operate();
	}
	
}
