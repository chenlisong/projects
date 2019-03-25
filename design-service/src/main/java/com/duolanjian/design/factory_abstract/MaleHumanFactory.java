package com.duolanjian.design.factory_abstract;

/**
 * 男性工厂
 * @author chenlisong
 *
 */
public class MaleHumanFactory extends AbstractHumanFactory{

	//制造一个男性黑人
	public Human createBlackHuman() {
		return createHuman(HumanEnum.MaleBlackHuman);
	}

	//制造一个男性白人
	public Human createWhiteHuman() {
		return createHuman(HumanEnum.MaleWhiteHuman);
	}

	//制造一个男性黄种人
	public Human createYellowHuman() {
		return createHuman(HumanEnum.MaleYellowHuman);
	}

}
