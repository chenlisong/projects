package com.duolanjian.design.mediator;

public class Purshase extends AbstractColleague{

	public Purshase(AbstractMediator _mediator) {
		super(_mediator);
	}

	public void buy(int number) {
		this.mediator.execute("purchase.buy", number);
	}
	
	public void refuseBuy() {
		System.out.println("refuse buy");
	}
}
