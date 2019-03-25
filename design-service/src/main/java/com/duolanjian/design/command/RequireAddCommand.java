package com.duolanjian.design.command;

public class RequireAddCommand extends Command{

	@Override
	public void execute() {
		
		super.requireGroup.find();
		
		super.requireGroup.plan();
		
		super.requireGroup.add();
		
	}

}
