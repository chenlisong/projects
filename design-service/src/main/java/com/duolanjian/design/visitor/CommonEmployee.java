package com.duolanjian.design.visitor;

public class CommonEmployee extends Employee{

	private String job;
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	//设置允许访问
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	public CommonEmployee(String name, Double salary, String job) {
		super.setName(name);
		super.setSalary(salary);
		this.job = job;
	}

}
