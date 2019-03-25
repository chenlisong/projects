package com.duolanjian.design.builder;

import java.util.ArrayList;
import java.util.List;

public class Director {
	
	private CarH1Builder carh1Builder = new CarH1Builder();
	
	private CarH2Builder carh2Builder = new CarH2Builder();
	
	private List<String> sequence = new ArrayList<String>();
	
	public CarH1 getCar1AModel() {
		
		sequence.clear();
		sequence.add("start");
		sequence.add("stop");
		CarModel carModel = carh1Builder.getCarModel();
		carModel.setSequence(sequence);
		return (CarH1)carModel;
	}
	
	public CarH1 getCar1BModel() {
		
		sequence.clear();
		sequence.add("start");
		sequence.add("enginBoom");
		sequence.add("alarm");
		CarModel carModel = carh1Builder.getCarModel();
		carModel.setSequence(sequence);
		return (CarH1)carModel;
	}
	
	public CarH2 getCar2AModel() {
		
		sequence.clear();
		sequence.add("start");
		sequence.add("stop");
		CarModel carModel = carh2Builder.getCarModel();
		carModel.setSequence(sequence);
		return (CarH2)carModel;
	}
	
	public CarH2 getCar2BModel() {
		
		sequence.clear();
		sequence.add("start");
		sequence.add("enginBoom");
		sequence.add("alarm");
		CarModel carModel = carh2Builder.getCarModel();
		carModel.setSequence(sequence);
		return (CarH2)carModel;
	}
	
}
