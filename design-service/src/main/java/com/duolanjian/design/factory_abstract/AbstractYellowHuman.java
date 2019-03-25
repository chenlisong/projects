package com.duolanjian.design.factory_abstract;

/**
 * 为什么要修改成抽象类呢？要定义性别呀
 * @author chenlisong
 *
 */
public abstract class AbstractYellowHuman implements Human{

	public void laught() {
		System.out.println("yellow laught...");
	}

	public void cry() {
		System.out.println("yellow cry...");
	}

	public void talk() {
		System.out.println("yellow talk...");
	}

}
