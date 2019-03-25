package com.company.java.exception;

public class ResourceNotExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ResourceNotExistException() {
		super();
	}
	
	public ResourceNotExistException(String s) {
		super(s);
	}
	
	public ResourceNotExistException(Throwable t) {
		super(t);
	}

}
