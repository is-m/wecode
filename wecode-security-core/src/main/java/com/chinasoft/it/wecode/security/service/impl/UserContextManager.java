package com.chinasoft.it.wecode.security.service.impl;

public class UserContextManager {

	private static ThreadLocal<String> user = new ThreadLocal<>();

	public static void set(String uid) {
		user.set(uid);
	}

	public static String get() {
		return user.get();
	}

	public static void remove() {
		if (user.get() != null) {
			user.remove();
		}
	}
}
