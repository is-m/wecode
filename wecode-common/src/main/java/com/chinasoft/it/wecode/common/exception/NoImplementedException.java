package com.chinasoft.it.wecode.common.exception;

public class NoImplementedException extends RuntimeException {
 
	private static final long serialVersionUID = -8924414963971296549L;

	public NoImplementedException() {
		super("未实现的内容");
	}

	public NoImplementedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoImplementedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoImplementedException(String message) {
		super(message);
	}

	public NoImplementedException(Throwable cause) {
		super(cause);
	}

}
