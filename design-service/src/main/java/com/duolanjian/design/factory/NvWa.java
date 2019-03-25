package com.duolanjian.design.factory;

/**
 * 女娲
 * @author chenlisong
 *
 */
public class NvWa {

	public static void main(String[] args) {
		
		System.out.println("女娲指定造人开始 ...");
		Human whiteHuman = HumanFactory.createHuman(WhiteHuman.class);
		whiteHuman.laught();
		whiteHuman.cry();
		whiteHuman.talk();
		System.out.println();
		
		Human blackHuman = HumanFactory.createHuman(BlackHuman.class);
		blackHuman.laught();
		blackHuman.cry();
		blackHuman.talk();
		System.out.println();
		
		Human yellowHuman = HumanFactory.createHuman(YellowHuman.class);
		yellowHuman.laught();
		yellowHuman.cry();
		yellowHuman.talk();
		System.out.println();
		System.out.println("女娲结束指定造人结束 ...\n\n");
		
		System.out.println("女娲随机造人开始 ...\n");
		for(int i = 0; i < 10; i ++) {
			Human random = HumanFactory.createHuman();
			random.laught();
			random.cry();
			random.talk();
			System.out.println();
		}
		System.out.println("女娲随机造人结束 ...");
		
	}
	
}
