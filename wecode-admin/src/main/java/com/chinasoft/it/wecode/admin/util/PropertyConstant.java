package com.chinasoft.it.wecode.admin.util;

public interface PropertyConstant {

	String ROOT = "root";

	/**
	 * 值类型：普通
	 */
	String VALUE_TYPE_simple = "simple";
	
	/**
	 * 值类型：混合（包含普通和列表）
	 */
	String VALUE_TYPE_blend = "blend";
	
	/**
	 * 值类型：项（所有混合类型中添加的子项为列表项）
	 */
	String VALUE_TYPE_item = "item";
}
