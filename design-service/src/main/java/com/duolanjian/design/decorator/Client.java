package com.duolanjian.design.decorator;

public class Client {

	public static void main(String[] args) {
		SchoolReport sr = new FourGradeReport();
		
		sr = new HighScoreReport(sr);
		
		sr = new SortScoreReport(sr);
		
		sr.report();
		
		sr.sign("王老汉");
	}
	
}
