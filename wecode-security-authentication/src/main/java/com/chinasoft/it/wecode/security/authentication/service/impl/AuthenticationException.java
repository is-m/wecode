package com.chinasoft.it.wecode.security.authentication.service.impl;

public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = -1859258067555053932L;

	public AuthenticationException() {
		super();
	}

	public AuthenticationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthenticationException(String message) {
		super(message);
	}

	public AuthenticationException(Throwable cause) {
		super(cause);
	}

}
