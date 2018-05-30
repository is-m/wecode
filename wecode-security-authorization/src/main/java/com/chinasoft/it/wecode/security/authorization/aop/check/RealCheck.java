package com.chinasoft.it.wecode.security.authorization.aop.check;

import com.chinasoft.it.wecode.common.util.CollectionUtils;
import com.chinasoft.it.wecode.security.AuthenticationException;
import com.chinasoft.it.wecode.security.AuthorizationException;
import com.chinasoft.it.wecode.security.Role;
import com.chinasoft.it.wecode.security.User;

/**
 * 每个
 * 
 * @author Administrator
 *
 */
public class RealCheck extends AbstractCheck {

	public RealCheck(Check component) {
		super(component);
	}

	@Override
	public void doCheck(String permissionCode) throws AuthenticationException, AuthorizationException {
		User currentUser = getCurrentUser();
		if (currentUser != null) {
			if (CollectionUtils.isEmpty(currentUser.getRoles())) {
				throw new AuthorizationException("no roles");
			}

			for (Role role : currentUser.getRoles()) {
				if (role.getPermissionCodeSet().contains(permissionCode)) {
					return;
				}
			}
			throw new AuthorizationException("no permission of code " + permissionCode);
		} else {
			throw new AuthenticationException("no logined,but access RealPermissionCheck.doCheck ");
		}
	}

}
