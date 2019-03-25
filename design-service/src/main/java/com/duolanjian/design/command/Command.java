package com.duolanjian.design.command;

public abstract class Command {

	protected RequireGroup requireGroup = new RequireGroup();

	protected PageGroup pageGroup = new PageGroup();

	protected CodeGroup codeGroup = new CodeGroup();
	
	public abstract void execute();
	
}
