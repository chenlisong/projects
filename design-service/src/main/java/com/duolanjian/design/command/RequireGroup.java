package com.duolanjian.design.command;

public class RequireGroup extends Group{

	@Override
	public void add() {
		System.out.println("需求组增加功能");
	}

	@Override
	public void delete() {
		System.out.println("需求组删除功能");
	}

	@Override
	public void change() {
		System.out.println("需求组修改功能");
	}

	@Override
	public void find() {
		System.out.println("找到需求组开会");
	}

	@Override
	public void plan() {
		System.out.println("需求组设定计划");
	}

}
