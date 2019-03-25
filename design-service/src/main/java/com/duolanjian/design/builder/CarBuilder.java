package com.duolanjian.design.builder;

import java.util.List;

public abstract class CarBuilder {

	public abstract void setSequence(List<String> sequence);
	
	public abstract CarModel getCarModel();
	
}
