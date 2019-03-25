package com.duolanjian.design.command;

public class CodeGroup extends Group{

	@Override
	public void add() {
		System.out.println("开发组增加功能");
	}

	@Override
	public void delete() {
		System.out.println("开发组删除功能");
	}

	@Override
	public void change() {
		System.out.println("开发组修改功能");
	}

	@Override
	public void find() {
		System.out.println("找到开发组开会");
	}

	@Override
	public void plan() {
		System.out.println("开发组设定计划");
	}

}
