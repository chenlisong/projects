package com.duolanjian.design.mediator;


public class Client {

	public static void main(String[] args) {
		AbstractMediator mediator = new Mediator();

		System.out.println("begin buy...");
		Purshase purchase = new Purshase(mediator);
		purchase.buy(100);
		System.out.println();

		System.out.println("begin sell...");
		Sale sale = new Sale(mediator);
		sale.sell(20);
		System.out.println();
		
		System.out.println("begin stock...");
		Stock stock = new Stock(mediator);
		stock.clearStock();
		System.out.println();
		
	}
}
