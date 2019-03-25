package com.duolanjian.design.state;

public class RunState extends LiftState {

	public void open() {
		//do nothing
	}

	public void close() {
		//do nothing
	}

	public void run() {
		System.out.println("lift run ...");
	}

	public void stop() {
		super.context.setLiftState(Context.stopState);
		super.context.getLiftState().stop();
	}

}
