package com.chinasoft.it.wecode.exception;

/**
 * 基础异常
 * @author Administrator
 *
 */
public abstract class BaseException extends RuntimeException {

  private static final long serialVersionUID = 7221124877274448672L;

  /**
   * 消息KEY
   */
  private String messageKey;

  public BaseException() {
    super();
  }

  public BaseException(String messageOrKey, Throwable cause) {
    super(messageOrKey, cause);
  }

  public BaseException(String messageOrKey) {
    super(messageOrKey);
  }

  public BaseException(Throwable cause) {
    super(cause);
  }

}
