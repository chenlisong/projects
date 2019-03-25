package com.duolanjian.design.bridge;

public class HourceCorp extends Corp{

	public HourceCorp(Hourse hourse) {
		super(hourse);
	}
	
	public void makeMoney() {
		super.makeMoney();
		System.out.println("hourse make much money.");
	}

}
