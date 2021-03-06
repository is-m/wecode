package com.chinasoft.it.wecode.security.authorization.aop.check;

import com.chinasoft.it.wecode.exception.AuthenticationException;
import com.chinasoft.it.wecode.exception.AuthorizationException;
import com.chinasoft.it.wecode.security.UserPrincipal;
import com.chinasoft.it.wecode.security.service.impl.UserContextManager;

public abstract class AbstractCheck implements Check {

	protected Check pack;

	public AbstractCheck(Check component) {
		this.pack = component;
	}

	protected UserPrincipal getCurrentUser() {
		return UserContextManager.get();
	}

	@Override
	public void check(String permissionCode) throws AuthenticationException, AuthorizationException {
		doCheck(permissionCode);
		if (pack != null) {
			pack.check(permissionCode);
		}
	}

	public abstract void doCheck(String permissionCode) throws AuthenticationException, AuthorizationException;
}
