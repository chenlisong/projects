package com.duolanjian.design.state;

/**
 * 状态的抽象
 * @author ChenLisong
 *
 */
public abstract class LiftState {
	
	//上下文，控制状态的调用
	protected Context context;
	
	public void setContext(Context _context) {
		this.context = _context;
	}

	//打开电梯
	public abstract void open();
	
	//关闭电梯
	public abstract void close();
	
	//运行电梯
	public abstract void run();
	
	//停止电梯
	public abstract void stop();
	
}
