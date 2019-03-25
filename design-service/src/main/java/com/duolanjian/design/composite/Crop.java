package com.duolanjian.design.composite;

public abstract class Crop {

	private String name = "";
	
	private String position = "";
	
	private Double salary = 0.0;
	
	private Crop parent = null;

	public Crop(String name, String position, Double salary) {
		this.name = name;
		this.position = position;
		this.salary = salary;
	}

	public String getInfo() {
		return "Crop [name=" + name + ", position=" + position + ", salary="
				+ salary + "]";
	}
	
	public void setParent(Crop crop) {
		this.parent = crop;
	}
	
	public Crop getParent() {
		return this.parent;
	}
	
}
