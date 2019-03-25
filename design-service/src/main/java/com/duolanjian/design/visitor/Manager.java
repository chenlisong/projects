package com.duolanjian.design.visitor;

public class Manager extends Employee {

	private String performance;
	
	public String getPerformance() {
		return performance;
	}

	public void setPerformance(String performance) {
		this.performance = performance;
	}

	//设置允许访问者访问
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	public Manager(String name, Double salary, String performance) {
		super.setName(name);
		super.setSalary(salary);
		this.performance = performance;
	}

}
