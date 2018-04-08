package com.chinasoft.it.wecode.admin.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.chinasoft.it.wecode.base.BaseEntity;

/**
 * 本地字符串（国际化）
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "sys_locale_string")
public class LocaleString extends BaseEntity {

	private static final long serialVersionUID = -3095519218140957350L;

	
}
