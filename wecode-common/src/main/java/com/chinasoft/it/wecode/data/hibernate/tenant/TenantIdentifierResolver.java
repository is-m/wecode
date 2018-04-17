package com.chinasoft.it.wecode.data.hibernate.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

/**
 * 租户标识解析器（演示类，仅用于指导，不作具体用途）
 * JPA应用需要多租户时可以参考该类的实现写法来实现具体内容
 * 
 * Schema 以及 DB 级别的多租户适用于表已经存在的场景下，又不想对DAO层进行大的修改的情况下使用
 * Column 级别的 多租户适用于数据量小最好是dao层未开发之前，因为开发之后意味着需要对所有接口添加字段过滤
 * 
 * @author Administrator
 *
 */
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

	/**
	 * 解析当前租户身份标识 
	 * @return 当前登录用户信息身上获取用户身上的租户标识
	 */
	@Override
	public String resolveCurrentTenantIdentifier() {
		return "currentUser.get  CompanyId() || cropId() || userId()";
	}

	/**
	 * 
	 */
	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}

}
