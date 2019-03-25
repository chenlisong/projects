package com.duolanjian.design.command;

public class Client {

	public static void main(String[] args) {
		Invoker invoker = new Invoker();
		invoker.setCommand(new RequireAddCommand());
		invoker.action();
		System.out.println();
		
		invoker.setCommand(new PageDeleteCommand());
		invoker.action();
		
	}
	
}
