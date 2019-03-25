package com.duolanjian.design.mediator;

import java.util.Random;

public class Sale extends AbstractColleague{
	
	public Sale(AbstractMediator _mediator) {
		super(_mediator);
	}
	
	public void sell(int number) {
		this.mediator.execute("sale.sell", number);
	}
	
	public void offsetSell() {
		this.mediator.execute("sale.offsetsell");
	}

	public int getSaleState() {
		Random random = new Random(System.currentTimeMillis());
		int saleState = random.nextInt(100);
		System.out.println("saleState:"+saleState);
		return saleState;
	}
}
