package com.company.java.exception;

public class FileNotExistException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public FileNotExistException() {
		super();
	}
	
	public FileNotExistException(String s) {
		super(s);
	}
	
	public FileNotExistException(Throwable t) {
		super(t);
	}
	
	public FileNotExistException(String msg, Throwable t) {
		super(msg, t);
	}

}
