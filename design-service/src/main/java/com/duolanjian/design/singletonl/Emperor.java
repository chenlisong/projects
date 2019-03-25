package com.duolanjian.design.singletonl;

/**
 * 中国的历史上一般都是一个朝代一个皇帝，有两个皇帝的话，必然要PK出一个皇帝出来
 * 但是多线程访问有问题
 * @author chenlisong
 *
 */
public class Emperor {

	public static Emperor emperor = null;
	
	//世俗和道德约束你，目的就是不让你产生第二个皇帝
	private Emperor() {}
	
	public static Emperor getInstance() {
		if(emperor == null) {
			emperor = new Emperor();
		}
		return emperor;
	}
	
	//皇帝叫什么名字呀
	public void info() {
		System.out.println("我是皇帝xxx。。。");
	}
}
