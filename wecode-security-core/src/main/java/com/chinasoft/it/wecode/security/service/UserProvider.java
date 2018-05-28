package com.chinasoft.it.wecode.security.service;

import com.chinasoft.it.wecode.security.User;

public interface UserProvider {

	public User get(String uid);
}
