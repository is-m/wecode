package com.chinasoft.it.wecode.asset.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.chinasoft.it.wecode.base.BaseEntity;

/**
 * 资产
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "as_asset")
public class Asset extends BaseEntity {

	private static final long serialVersionUID = -466549261221760642L;

	/**
	 * asset name
	 */
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
