package com.chinasoft.it.wecode.security.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.chinasoft.it.wecode.base.BaseEntity;

/**
 * 用户组
 * 
 * TODO:用户组，数据范围（维度）等可能不常用的安全组件可能需要单独拧出来作为独立的安全模块补充模块来设计，以防无效的加载
 * @author Administrator
 *
 */
/*@Entity
@Table(name="sys_usergroup")*/
public class UserGroup extends BaseEntity {

  /**
   * 用户组名称
   */
  private String name;
  
  /**
   * 用户组角色
   */
  private Set<Role> role;
  
  /**
   * 数据范围
   */
  private Set<DataRange> dataRange;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Role> getRole() {
    return role;
  }

  public void setRole(Set<Role> role) {
    this.role = role;
  }

  public Set<DataRange> getDataRange() {
    return dataRange;
  }

  public void setDataRange(Set<DataRange> dataRange) {
    this.dataRange = dataRange;
  }
  
}
