package com.duolanjian.design.strategy;

public class Zhaoyun {

	/**
	 * 赵云出场了，他根据诸葛亮给他的交代，依次拆开妙计 
	 * @param args
	 */
	public static void main(String[] args) {
		Context context;
		System.out.println("-----------刚刚到吴国的时候拆第一个-------------");   
		context = new Context(new BackDoor()); 
		//拿到妙计   
		context.operate();  
		//拆开执行  
		System.out.println("\n\n");   

		System.out.println("-----------刘备乐不思蜀了，拆第二个了-------------");   
		context = new Context(new GiveGreenLight()); 
		//拿到妙计   
		context.operate();  
		//拆开执行  
		System.out.println("\n\n");   

		System.out.println("-----------孙权的小兵追了，咋办？拆第三个 -------------");   
		context = new Context(new BlockEnemy()); 
		//拿到妙计   
		context.operate();  
		//拆开执行  
		System.out.println("\n\n");   
		
	}
	
}
