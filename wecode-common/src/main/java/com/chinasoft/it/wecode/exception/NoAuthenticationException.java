package com.chinasoft.it.wecode.exception;

/**
 * 未认证/未登录 异常
 * @author Administrator
 *
 */
public class NoAuthenticationException extends BaseException implements HttpCodeProvider {

  /**
   * 
   */
  private static final long serialVersionUID = 4465175753656408704L;

  @Override
  public int getCode() {
    return 401;
  }

  public NoAuthenticationException() {
    super("no found accessor authentication");
  }

  public NoAuthenticationException(String messageOrKey, Throwable cause) {
    super(messageOrKey, cause);
  }

  public NoAuthenticationException(String messageOrKey) {
    super(messageOrKey);
  }

  public NoAuthenticationException(Throwable cause) {
    super(cause);
  }



}
