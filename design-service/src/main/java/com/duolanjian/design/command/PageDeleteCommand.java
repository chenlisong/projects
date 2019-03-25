package com.duolanjian.design.command;

public class PageDeleteCommand extends Command{

	@Override
	public void execute() {
		
		super.pageGroup.find();
		
		super.pageGroup.plan();
		
		super.pageGroup.delete();
		
		super.codeGroup.delete();
		
	}

}
