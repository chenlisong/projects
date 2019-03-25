package com.duolanjian.design.state;

/**
 * 打开状态，由状态本身来控制本身状态是否允许眺往别的状态
 * @author ChenLisong
 *
 */
public class OpenState extends LiftState {

	//打开状态，可以执行打开
	public void open() {
		System.out.println("lift open ...");
	}

	//可以执行关闭状态，先设置上下文中的当前状态是关闭，然后执行关闭的方法，打印信息
	public void close() {
		super.context.setLiftState(Context.closeState);
		super.context.getLiftState().close();
		
	}

	//不能跳往运行状态
	public void run() {
		//do nothing
	}

	//不能眺往停止状态
	public void stop() {
		//do nothing
	}

}
