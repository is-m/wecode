package com.chinasoft.it.wecode.security;

/**
 * 认证异常
 * 
 * @author Administrator
 *
 */
public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = -8283553208562884376L;

	public AuthenticationException() {
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
