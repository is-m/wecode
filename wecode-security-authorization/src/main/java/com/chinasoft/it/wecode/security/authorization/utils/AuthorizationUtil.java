package com.chinasoft.it.wecode.security.authorization.utils;

import com.chinasoft.it.wecode.annotations.security.Module;
import com.chinasoft.it.wecode.annotations.security.Operate;
import com.chinasoft.it.wecode.common.util.StringUtil;

import static com.chinasoft.it.wecode.security.authorization.utils.AuthorizationConstant.SPERATOR;

/**
 * 认证工具类
 * 
 * @author Administrator
 *
 */
public class AuthorizationUtil {

	/**
	 * 获取权限代码
	 * 
	 * @param module
	 * @param defaultModuleName
	 * @param operate
	 * @param defaultOperationName
	 * @return
	 */
	public static String getPermissionCode(Module module, String defaultModuleName, Operate operate,
			String defaultOperationName) {
		String mname = StringUtil.getString(module.code(), defaultModuleName);
		String oname = StringUtil.getString(operate.code(), defaultOperationName);
		return String.format("SYS%s%s%s%s", SPERATOR, mname, SPERATOR, oname);
	}
}
