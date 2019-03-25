package com.company.java.exception;

public class ResourceNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
		super();
	}
	
	public ResourceNotFoundException(String s) {
		super(s);
	}
	
	public ResourceNotFoundException(Throwable t) {
		super(t);
	}
	
	public ResourceNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}

}
