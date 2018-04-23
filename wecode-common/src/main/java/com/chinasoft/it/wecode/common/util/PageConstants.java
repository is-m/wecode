package com.chinasoft.it.wecode.common.util;

/**
 * 分页常量
 * 
 * @author Administrator
 *
 */
public interface PageConstants {

	/**
	 * 起始页
	 * 该值为0时，是默认方式，为1时需要人为干涉 Pageable 参数的起始页
	 */
	int START_PAGE = 1;
	
	/**
	 * 默认页大小
	 */
	int DEFAULT_SIZE = 15;

	/**
	 * 最大页大小
	 */
	int MAX_SIZE = 500;

	/**
	 * 最小页大小
	 */
	int MIN_SIZE = 1;

	/**
	 * 取参：页大小
	 */
	String PARAM_SIZE = "pageSize";

	/**
	 * 取参：当前页
	 */
	String PARAM_PAGE = "curPage";

	
	/**
	 * 取参位置：查询参数
	 */
	String PARAM_HTTP_TYPE_QUERY = "query";
	
	/**
	 * 取参位置：路径参数
	 */
	String PARAM_HTTP_TYPE_PATH = "path";
	
	/**
	 * 取参位置：header
	 */
	String PARAM_HTTP_TYPE_HEADER = "header";
	
	/**
	 * 默认取参位置
	 */
	String PARAM_HTTP_TYPE_DEFAULT = PARAM_HTTP_TYPE_QUERY;
}
