package com.chinasoft.it.wecode.exception;

/**
 * 异常：密码无效/不正确
 */
public class PasswordInvalidException extends AuthenticationException {

    public PasswordInvalidException() {
        super("密码不正确");
    }
}
