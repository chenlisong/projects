package com.company.java.exception;

public class BuildRepeatException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public BuildRepeatException() {
		super();
	}
	
	public BuildRepeatException(String s) {
		super(s);
	}
	
	public BuildRepeatException(Throwable t) {
		super(t);
	}
	
	public BuildRepeatException(String msg, Throwable t) {
		super(msg, t);
	}

}
