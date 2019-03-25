package com.duolanjian.design.mediator;


public class Stock extends AbstractColleague{

	public Stock(AbstractMediator _mediator) {
		super(_mediator);
	}
	
	public static int number = 100;

	public int getNumber() {
		return number;
	}

	public void increase(int _number) {
		number += _number;
		System.out.println("stock number:" + number);
	}

	public void decrease(int _number) {
		number -= _number;
		System.out.println("stock number:" + number);
	}

	public void clearStock() {
		System.out.println("clear stock");
		this.mediator.execute("stock.clear");
	}
	
}
