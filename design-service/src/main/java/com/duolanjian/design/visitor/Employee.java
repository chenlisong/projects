package com.duolanjian.design.visitor;

public abstract class Employee {

	private String name;
	
	private Double salary;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public abstract void accept(IVisitor visitor); 
	
}
