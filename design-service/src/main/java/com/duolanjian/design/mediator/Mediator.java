package com.duolanjian.design.mediator;

public class Mediator extends AbstractMediator{

	@Override
	public void execute(String str, Object... objects) {
		if("purchase.buy".equals(str)) {
			this.buy((Integer) objects[0]);
		}else if("sale.sell".equals(str)) {
			this.sell((Integer) objects[0]);
		}else if("sale.offsetsell".equals(str)) {
			this.offSell();
		}else if("stock.clear".equals(str)) {
			this.clearStock();
		}
	}
	
	public void buy(int number) {
		int saleState = this.sale.getSaleState();
		if(saleState > 80) {
			System.out.println("buy number:"+number);
		}else {
			number = number/2;
			System.out.println("buy number:"+number);
		}
		this.stock.increase(number);
	}
	
	public void sell(int number) {
		if(super.stock.getNumber() < number) {
			this.purshase.buy(number);
		}
		System.out.println("sell number:"+number);
		this.stock.decrease(number);
	}
	
	public void offSell() {
		System.out.println("off sell number:"+this.stock.getNumber());
		this.stock.decrease(stock.getNumber());
	}
	
	public void clearStock() {
		this.sale.offsetSell();
		this.purshase.refuseBuy();
	}
}
