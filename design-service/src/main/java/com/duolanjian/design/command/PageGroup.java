package com.duolanjian.design.command;

public class PageGroup extends Group{

	@Override
	public void add() {
		System.out.println("美工组增加功能");
	}

	@Override
	public void delete() {
		System.out.println("美工组删除功能");
	}

	@Override
	public void change() {
		System.out.println("美工组修改功能");
	}

	@Override
	public void find() {
		System.out.println("找到美工组开会");
	}

	@Override
	public void plan() {
		System.out.println("美工组设定计划");
	}

}
