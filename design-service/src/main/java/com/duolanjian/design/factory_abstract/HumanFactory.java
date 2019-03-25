package com.duolanjian.design.factory_abstract;

public interface HumanFactory {

	//制造黑人
	public Human createBlackHuman();
	
	//制造白人
	public Human createWhiteHuman();
	
	//制造黄种人
	public Human createYellowHuman();
	
}
