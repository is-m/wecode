package com.chinasoft.it.wecode.common.lock;

public class LockAccessException extends RuntimeException {

  private static final long serialVersionUID = -6475758093079185012L;

  public LockAccessException() {
    super();
  }

  public LockAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public LockAccessException(String message, Throwable cause) {
    super(message, cause);
  }

  public LockAccessException(String message) {
    super(message);
  }

  public LockAccessException(Throwable cause) {
    super(cause);
  }



}
