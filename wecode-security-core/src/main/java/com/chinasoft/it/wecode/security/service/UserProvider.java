package com.chinasoft.it.wecode.security.service;

import com.chinasoft.it.wecode.security.UserPrincipal;

public interface UserProvider {

	UserPrincipal get(String uid);
}
