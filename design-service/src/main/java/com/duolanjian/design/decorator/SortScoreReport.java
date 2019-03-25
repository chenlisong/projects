package com.duolanjian.design.decorator;

public class SortScoreReport extends Decorator{

	public SortScoreReport(SchoolReport sr) {
		super(sr);
	}
	
	public void reportSort() {
		System.out.println("排名从40到30名");
	}
	
	@Override
	public void report() {
		super.report();
		this.reportSort();
	}

}
