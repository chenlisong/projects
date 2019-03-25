package com.duolanjian.design.mediator;

public abstract class AbstractMediator {

	protected Stock stock;
	
	protected Purshase purshase;
	
	protected Sale sale;
	
	public AbstractMediator(){
		stock = new Stock(this);
		purshase = new Purshase(this);
		sale = new Sale(this);
	}
	
	public abstract void execute(String str, Object...objects);
	
}
