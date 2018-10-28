package com.chinasoft.it.wecode.security.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.chinasoft.it.wecode.base.BaseEntity;

/**
 * 用户
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "s_user")
public class User extends BaseEntity {

  /**
   * 姓名
   */
  private String name;

  /**
   * 密码
   */
  private String password;

  /**
   * 备注
   */
  private String remark;

  /**
   * 电子邮箱
   */
  private String mail;

  /**
   * 移动号码 MP MobilePhone
   */
  private String mobilePhone;

  /**
   * 状态，0：失效，1：生效
   */
  private Integer status;
/*
  @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
  @JoinTable(name = "sys_user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
  private Set<Role> roles = new HashSet<>();*/

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public User() {}

  public User(Serializable id, String password, Integer status) {
    setId((String) id);
    this.password = password;
    this.status = status;
  }


}
