package com.duolanjian.design.state;

public class Client {

	public static void main(String[] args) {
		//设置状态调用的上下文
		Context context = new Context();
		//设置起始状态是关闭状态
		context.setLiftState(new CloseState());
		
		context.open();
		context.close();
		context.run();
		context.stop();
		//这个不应该被执行，因为停止状态不能到达运行状态，得先执行close
		context.run();
		
	}
}
