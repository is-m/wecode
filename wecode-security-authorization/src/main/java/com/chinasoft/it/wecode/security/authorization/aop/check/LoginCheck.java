package com.chinasoft.it.wecode.security.authorization.aop.check;

import com.chinasoft.it.wecode.security.AuthenticationException;
import com.chinasoft.it.wecode.security.AuthorizationException;

/**
 * 登录检查
 * @author Administrator
 *
 */
public class LoginCheck extends AbstractCheck {

	public LoginCheck(Check component) {
		super(component);
	}

	@Override
	public void doCheck(String permissionCode) throws AuthenticationException, AuthorizationException {
		if(getCurrentUser() == null) {
			throw new AuthenticationException("no login.");
		}
	} 

}
