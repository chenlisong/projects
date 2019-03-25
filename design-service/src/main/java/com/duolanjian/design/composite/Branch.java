package com.duolanjian.design.composite;

import java.util.ArrayList;
import java.util.List;

public class Branch extends Crop{

	private List<Crop> subOrdinateList = new ArrayList<Crop>();
	
	public Branch(String name, String position, Double salary) {
		super(name, position, salary);
	}
	
	public void addSubOrdinate(Crop crop) {
		crop.setParent(this);
		this.subOrdinateList.add(crop);
	}
	
	public List<Crop> getSubOrdinate() {
		return this.subOrdinateList;
	}

}
