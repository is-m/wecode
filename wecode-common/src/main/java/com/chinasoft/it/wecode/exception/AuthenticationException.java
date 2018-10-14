package com.chinasoft.it.wecode.exception;

/**
 * 未认证/未登录 异常
 * @author Administrator
 *
 */
public class AuthenticationException extends BaseException implements HttpCodeProvider {

  /**
   * 
   */
  private static final long serialVersionUID = 4465175753656408704L;

  @Override
  public int getCode() {
    return 401;
  }

  public AuthenticationException() {
    super("no found accessor authentication");
  }

  public AuthenticationException(String messageOrKey, Throwable cause) {
    super(messageOrKey, cause);
  }

  public AuthenticationException(String messageOrKey) {
    super(messageOrKey);
  }

  public AuthenticationException(Throwable cause) {
    super(cause);
  }



}
