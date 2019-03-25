package com.duolanjian.design.visitor;

import java.util.ArrayList;
import java.util.List;

public class Client {

	public static void main(String[] args) {
		
		Visitor visitor = new Visitor();
		for(Employee employee : getData()) {
			employee.accept(visitor);
		}
		
	}
	
	public static List<Employee> getData() {
		List<Employee> list = new ArrayList<Employee>();
		
		list.add(new CommonEmployee("张三", 1000.0, "撸Java"));
		list.add(new CommonEmployee("李四", 1100.0, "撸JS"));
		list.add(new Manager("王五", 11000.0, "吹牛比"));
		
		return list;
	}
}
