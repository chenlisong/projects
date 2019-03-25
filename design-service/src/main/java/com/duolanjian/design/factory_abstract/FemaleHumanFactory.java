package com.duolanjian.design.factory_abstract;

public class FemaleHumanFactory extends AbstractHumanFactory{

	public Human createBlackHuman() {
		return createHuman(HumanEnum.FemaleBlackHuman);
	}

	public Human createWhiteHuman() {
		return createHuman(HumanEnum.FemaleWhiteHuman);
	}

	public Human createYellowHuman() {
		return createHuman(HumanEnum.FemaleYellowHuman);
	}

}
