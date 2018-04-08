package com.chinasoft.it.wecode.security.domain;

import com.chinasoft.it.wecode.base.BaseEntity;

/**
 * 维度
 * 
 * @author Administrator
 *
 */
public class Dimension extends BaseEntity {

	private static final long serialVersionUID = -5665487992286962261L;

	/**
	 * 维度类型:数据字典项，外部链接，JSON数组字符串([{name:"深圳",value:"sz"}])，普通逗号分割约定字符串（深圳:sz,广东:gd,）
	 */
	private String type;
	
	/**
	 * 维度源，根艺维度类型不同而不同，例如为数据字典时，该值存放的应该为数据字典的路径，外部链接时，应该是个URL，依次类推
	 */
	private String src;
	
	
	/**
	 * 解析源的扩展内容，跟据源返回的值，最终要求解析的结构为[{name:"",value:""}]
	 */
	private String extention;


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getSrc() {
		return src;
	}


	public void setSrc(String src) {
		this.src = src;
	}


	public String getExtention() {
		return extention;
	}


	public void setExtention(String extention) {
		this.extention = extention;
	}
	
	
}
