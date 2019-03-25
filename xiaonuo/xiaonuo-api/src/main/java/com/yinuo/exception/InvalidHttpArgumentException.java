package com.yinuo.exception;

public class InvalidHttpArgumentException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidHttpArgumentException() {
		super();
	}
	
	public InvalidHttpArgumentException(String s) {
		super(s);
	}
	
	public InvalidHttpArgumentException(Throwable t) {
		super(t);
	}
	
	public InvalidHttpArgumentException(String msg, Throwable t) {
		super(msg, t);
	}

}
