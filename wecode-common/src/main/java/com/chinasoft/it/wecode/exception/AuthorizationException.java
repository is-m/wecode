package com.chinasoft.it.wecode.exception;

public class AuthorizationException extends BaseException implements HttpCodeProvider {

  private static final long serialVersionUID = -8563227061474562236L;

  @Override
  public int getCode() {
    return 403;
  }

  public AuthorizationException() {
    super();
  }

  public AuthorizationException(String messageOrKey, Throwable cause) {
    super(messageOrKey, cause);
  }

  public AuthorizationException(String messageOrKey) {
    super(messageOrKey);
  }

  public AuthorizationException(Throwable cause) {
    super(cause);
  }



}
