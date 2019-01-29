package com.chinasoft.it.wecode.common.lock;

/**
 * 锁超时异常
 * @author Administrator
 *
 */
public class LockTimeoutException extends RuntimeException {

  private static final long serialVersionUID = 3231256509841224757L;

  public LockTimeoutException() {
    super();
  }

  public LockTimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public LockTimeoutException(String message, Throwable cause) {
    super(message, cause);
  }

  public LockTimeoutException(String message) {
    super(message);
  }

  public LockTimeoutException(Throwable cause) {
    super(cause);
  }


}
