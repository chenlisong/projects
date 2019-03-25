package com.test.exception;

public class InvalidArgumentException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidArgumentException() {
		super();
	}
	
	public InvalidArgumentException(String s) {
		super(s);
	}
	
	public InvalidArgumentException(Throwable t) {
		super(t);
	}
	
	public InvalidArgumentException(String msg, Throwable t) {
		super(msg, t);
	}

}
