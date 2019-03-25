package com.duolanjian.design.multition;

/**
 * 大臣们悲惨了，一个皇帝都伺候不过来了，现在还来了两个个皇帝
 * TND，不管了，找到个皇帝，磕头，请按就成了！
 * @author chenlisong
 *
 */
public class Minister {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		int ministerNum = 10;
		
		for(int i=0; i<ministerNum; i++) {
			Emperor emperor = Emperor.getInstance();
			System.out.println("参拜的大臣是："+i);
			emperor.emperorInfo();
		}
		
	}
	
}
