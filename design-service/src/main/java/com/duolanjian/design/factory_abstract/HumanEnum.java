package com.duolanjian.design.factory_abstract;

public enum HumanEnum {

	FemaleBlackHuman("com.duolanjian.design.factory_abstract.FemaleBlackHuman"),

	MaleBlackHuman("com.duolanjian.design.factory_abstract.MaleBlackHuman"),

	FemaleWhiteHuman("com.duolanjian.design.factory_abstract.FemaleWhiteHuman"),

	MaleWhiteHuman("com.duolanjian.design.factory_abstract.MaleWhiteHuman"),

	FemaleYellowHuman("com.duolanjian.design.factory_abstract.FemaleYellowHuman"),

	MaleYellowHuman("com.duolanjian.design.factory_abstract.MaleYellowHuman");
	
	String className;
	
	HumanEnum(String className) {
		this.className = className;
	}
	
	public String getValue() {
		return className;
	}
}
