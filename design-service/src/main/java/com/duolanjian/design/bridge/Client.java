package com.duolanjian.design.bridge;

public class Client {

	public static void main(String[] args) {
		//老板主要业务，房地产公司
		HourceCorp hourseCorp = new HourceCorp(new Hourse());
		hourseCorp.makeMoney();
		System.out.println();
		
		//山寨公司-卖衣服
		CopyCorp copyCorp = new CopyCorp(new Clothes());
		copyCorp.makeMoney();
		System.out.println();
		
		//山寨公司-卖ipod
		copyCorp = new CopyCorp(new Ipod());
		copyCorp.makeMoney();
	}
	
}
