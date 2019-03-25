package com.duolanjian.design.bridge;

public class CopyCorp extends Corp{

	public CopyCorp(Product product) {
		super(product);
	}
	
	public void makeMoney() {
		super.makeMoney();
		System.out.println("copy make less money.");
	}

}
