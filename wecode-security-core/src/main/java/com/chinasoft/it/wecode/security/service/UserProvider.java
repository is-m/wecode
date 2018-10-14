package com.chinasoft.it.wecode.security.service;

import com.chinasoft.it.wecode.security.UserPrincipal;

public interface UserProvider {

	public UserPrincipal get(String uid);
}
