package com.duolanjian.design.decorator;


public class HighScoreReport extends Decorator{

	public HighScoreReport(SchoolReport sr) {
		super(sr);
	}
	
	public void reportHigh() {
		System.out.println("最高分是70");
	}
	
	@Override
	public void report() {
		super.report();
		this.reportHigh();
	}

}
