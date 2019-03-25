package com.duolanjian.design.state;

/**
 * 调用各个状态的上下文
 * @author ChenLisong
 *
 */
public class Context {
	
	//存储各个状态
	public static final OpenState openState = new OpenState();
	
	public static final CloseState closeState = new CloseState();
	
	public static final RunState runState = new RunState();
	
	public static final StopState stopState = new StopState();
	
	//保存当前的状态值
	public LiftState liftState;
	
	//因为状态更新而设置新的状态值
	public void setLiftState(LiftState liftState) {
		this.liftState = liftState;
		this.liftState.setContext(this);
	}
	
	//获取当前状态进行操作
	public LiftState getLiftState() {
		return this.liftState;
	}
	
	//打开操作
	public void open() {
		this.liftState.open();
	}
	
	//关闭操作
	public void close() {
		this.liftState.close();
	}
	
	//运行
	public void run() {
		this.liftState.run();
	}
	
	//关闭
	public void stop() {
		this.liftState.stop();
	}

}
