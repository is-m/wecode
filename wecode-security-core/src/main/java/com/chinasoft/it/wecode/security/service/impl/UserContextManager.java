package com.chinasoft.it.wecode.security.service.impl;

import java.util.List;

import com.chinasoft.it.wecode.common.util.ApplicationUtils;
import com.chinasoft.it.wecode.security.Role;
import com.chinasoft.it.wecode.security.User;
import com.chinasoft.it.wecode.security.service.UserProvider;

public class UserContextManager {

	private static ThreadLocal<User> $ = new ThreadLocal<>();

	public static void set(User user) {
		$.set(user);
	}

	public static void set(String uid) {
		// 加载用户信息
		//UserProvider bean = ApplicationUtils.getBean(UserProvider.class);
		//$.set(bean.get(uid));
		$.set(new User() {
			
			@Override
			public String getUid() { 
				return uid;
			}
			
			@Override
			public String getSecret() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<Role> getRoles() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Role getActivedRole() {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}

	public static User get() {
		return $.get();
	}

	public static void remove() {
		if ($.get() != null) {
			$.remove();
		}
	}
}
