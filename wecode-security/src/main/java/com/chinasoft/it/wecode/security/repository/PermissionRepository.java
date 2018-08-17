package com.chinasoft.it.wecode.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.chinasoft.it.wecode.security.domain.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String>, JpaSpecificationExecutor<Permission> {

	/**
	 * 根据代码获取权限
	 * 
	 * @param code
	 * @return
	 */
	Permission findOneByCodeAndType(String code, String type);

	/**
	 * 根据父节点获取内容
	 * 
	 * @param pid
	 * @param type
	 * @return
	 */
	List<Permission> findByPidAndType(String pid, String type);

	/**
	 * 根据代码反查不在权限代码范围的模块数据
	 * 
	 * @param persistCodes
	 *            保留的权限模块列表
	 * @param type
	 *            类型
	 * @return
	 */
	List<Permission> findByCodeNotInAndType(List<String> persistCodes, String type);
	
	/**
	 * 根据权限代码与类型获取权限数据
	 * 
	 * @param persistCodes
	 * @param type
	 * @return
	 */
	List<Permission> findByCodeInAndType(List<String> persistCodes, String type);

	/**
	 * 根据父节点ID查找
	 * @param parentIdList
	 * @return
	 */
	List<Permission> findByPidIn(List<String> parentIdList);
}
