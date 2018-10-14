package com.chinasoft.it.wecode.security.authorization.aop.check;

import com.chinasoft.it.wecode.common.util.CollectionUtils;
import com.chinasoft.it.wecode.exception.AuthenticationException;
import com.chinasoft.it.wecode.exception.AuthorizationException;
import com.chinasoft.it.wecode.security.UserPrincipal;

/**
 * 有效角色检查
 * @author Administrator
 *
 */
public class RoleCheck extends AbstractCheck {

	public RoleCheck(Check component) {
		super(component);
	}

	@Override
	public void doCheck(String permissionCode) throws AuthenticationException, AuthorizationException {
		UserPrincipal currentUser = getCurrentUser();
		if (currentUser != null) {
			if (CollectionUtils.isEmpty(currentUser.getRoles())) {
				throw new AuthorizationException("no valid role for system");
			}
		} else {
			throw new NullPointerException("no logined,cannot check role valid. ");
		}
	}

}
