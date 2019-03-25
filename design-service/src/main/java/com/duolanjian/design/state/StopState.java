package com.duolanjian.design.state;

public class StopState extends LiftState {

	public void open() {
		super.context.setLiftState(Context.openState);
		super.context.getLiftState().open();
	}

	public void close() {
		super.context.setLiftState(Context.closeState);
		super.context.getLiftState().close();
	}

	public void run() {
		//do nothing
	}

	public void stop() {
		System.out.println("lift stop...");
	}

}
