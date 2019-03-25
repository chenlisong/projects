package com.duolanjian.design.interpreter;

import java.util.HashMap;

public class Client {

	public static void main(String[] args) {
		String expStr = "a+b-c";
		HashMap<String,Integer> var = new HashMap<String, Integer>();
		var.put("a", 100);
		var.put("b", 5);
		var.put("c", 10);
		
		Calculator cal = new Calculator(expStr);
		System.out.println(cal.run(var));
	}
}
