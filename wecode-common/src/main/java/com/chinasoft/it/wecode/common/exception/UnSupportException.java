package com.chinasoft.it.wecode.common.exception;

/**
 * 不支持异常
 * 
 * @author Administrator
 *
 */
public class UnSupportException extends RuntimeException {

	private static final long serialVersionUID = -907171522741184443L;

	public UnSupportException() {
		super("暂不支持");
	}

	public UnSupportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnSupportException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnSupportException(String message) {
		super(message);
	}

	public UnSupportException(Throwable cause) {
		super(cause);
	}

}
