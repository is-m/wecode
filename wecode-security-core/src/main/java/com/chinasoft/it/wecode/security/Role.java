package com.chinasoft.it.wecode.security;

import java.util.List;

public interface Role {

	List<Permission> getPermissions();
}
