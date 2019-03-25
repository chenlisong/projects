package com.duolanjian.design.singletonl;

public class Minister {
	
	//大臣是天天要面见皇帝，今天见的皇帝和昨天的，前天不一样那就出问题了！
	public static void main(String[] args) {
		Emperor emperor1 = Emperor.getInstance();
		emperor1.info();
		
		Emperor emperor2 = Emperor.getInstance();
		emperor2.info();
		
		Emperor emperor3 = Emperor.getInstance();
		emperor3.info();
		
		//三天见的皇帝都是同一个人，荣幸吧！
	}

}
