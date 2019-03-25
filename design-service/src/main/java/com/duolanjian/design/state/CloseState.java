package com.duolanjian.design.state;

public class CloseState extends LiftState {

	public void open() {
		super.context.setLiftState(Context.openState);
		super.context.getLiftState().open();
	}

	public void close() {
		System.out.println("lift close ...");
	}

	public void run() {
		super.context.setLiftState(Context.runState);
		super.context.getLiftState().run();
	}

	public void stop() {
		super.context.setLiftState(Context.stopState);
		super.context.getLiftState().stop();
	}

}
