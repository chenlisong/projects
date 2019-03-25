package com.duolanjian.design.builder;

import java.util.List;

public class CarH1Builder extends CarBuilder{

	private CarModel car = new CarH1();

	@Override
	public void setSequence(List<String> sequence) {
		this.car.setSequence(sequence);
	}

	@Override
	public CarModel getCarModel() {
		return this.car;
	}
	
}
