package com.test.exception;

public class InternalException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InternalException() {
		super();
	}
	
	public InternalException(String s) {
		super(s);
	}
	
	public InternalException(Throwable t) {
		super(t);
	}

}
