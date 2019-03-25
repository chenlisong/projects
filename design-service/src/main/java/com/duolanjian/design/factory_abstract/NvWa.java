package com.duolanjian.design.factory_abstract;

/**
 * 女娲建立起了两条生产线，分别是：
 * 男性生产线
 * 女性生产线
 * @author chenlisong
 *
 */
public class NvWa {

	public static void main(String[] args) {
		
		//男性生产线
		HumanFactory maleHumanFactory = new MaleHumanFactory();

		//女性生产线
		HumanFactory femaleHumanFactory = new FemaleHumanFactory();

		Human maleYellowHuman = maleHumanFactory.createYellowHuman();
		
		Human femaleYellowHuman = femaleHumanFactory.createYellowHuman();

		maleYellowHuman.laught();
		maleYellowHuman.cry();
		maleYellowHuman.talk();
		maleYellowHuman.sex();

		System.out.println();
		femaleYellowHuman.laught();
		femaleYellowHuman.cry();
		femaleYellowHuman.talk();
		femaleYellowHuman.sex();
		
	}
	
}
