package com.duolanjian.design.singletonl;

/**
 * 通用单例模式
 * @author chenlisong
 *
 */
public class Singleton {

	public static Singleton singleton = new Singleton();
	
	//限制住不能直接产生一个实例
	private Singleton(){}
	
	public static Singleton getInstance() {
		return singleton;
	}
	
}
