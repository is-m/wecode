package com.chinasoft.it.wecode.security.authorization.aop.check;

import com.chinasoft.it.wecode.exception.AuthenticationException;
import com.chinasoft.it.wecode.exception.AuthorizationException;

public interface Check {

	void check(String permissionCode) throws AuthenticationException, AuthorizationException;
}
