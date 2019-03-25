package com.duolanjian.design.visitor;

public class Visitor implements IVisitor {

	public void visit(CommonEmployee commonEmployee) {
		System.out.println(this.getCommonEmployeeInfo(commonEmployee));
	}

	public void visit(Manager manager) {
		System.out.println(this.getManagerInfo(manager));
	}
	
	public String getBasicInfo(Employee employee) {
		return employee.getName() + "\t" + employee.getSalary() + "\t";
	}

	public String getManagerInfo(Manager manager) {
		
		return this.getBasicInfo(manager) + manager.getPerformance() + "\t";
	}

	public String getCommonEmployeeInfo(CommonEmployee commonEmployee) {
		
		return this.getBasicInfo(commonEmployee) + commonEmployee.getJob() + "\t";
	}
	
}
