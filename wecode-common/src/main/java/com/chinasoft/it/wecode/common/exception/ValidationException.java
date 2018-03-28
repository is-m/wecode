package com.chinasoft.it.wecode.common.exception;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 8265291900913064594L;

	public ValidationException() {
		super();
	}

	public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace); 
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause); 
	}

	public ValidationException(String message) {
		super(message); 
	}

	public ValidationException(Throwable cause) {
		super(cause); 
	}

}
