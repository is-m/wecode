package com.chinasoft.it.wecode.sign.util;

import org.springframework.util.StringUtils;

import com.chinasoft.it.wecode.sign.domain.Catelog;

@Deprecated
public class CatelogHelper {

	public static boolean isRoot(Catelog catelog) {
		String pid = catelog.getPid();
		return StringUtils.isEmpty(pid) || "root".equals(catelog.getPid());
	}
}
