package com.yinuo.exception;

public class NeedAuthorizationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String loginUrl;
	
	public NeedAuthorizationException() {
		super();
	}

	public NeedAuthorizationException(Throwable t) {
		super(t);
	}
	
	public NeedAuthorizationException(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
}
