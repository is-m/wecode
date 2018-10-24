package com.chinasoft.it.wecode.sign.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.chinasoft.it.wecode.base.BaseEntity;

@Entity
@Table(name = "s_catelog")
public class Catelog extends BaseEntity {

  /**
   * 父节点
   */
  private String pid;

  /**
   * 栏目名称
   */
  private String name;

  /**
   * 栏目短路径
   */
  private String uri;

  /**
   * 图标
   */
  private String icon;

  /**
   * 顺序
   */
  private Integer seq;

  /**
   * 状态：0 失效，1 生效（默认）
   */
  private Integer status;

  /**
   * 允许访问类型，all:全部可见(默认)，children:有子栏目时可见,authority:有权限时可见
   */
  private String allowType;

  /**
   * 允许访问值，在访问类型为有权限可见时应该需要设置该值,通常用于绑定功能权限
   */
  private String allowValue;

  /**
   * 目标类型，page:站内跳转(默认)，window：新页面
   */
  private String target;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getSeq() {
    return seq;
  }

  public void setSeq(Integer seq) {
    this.seq = seq;
  }

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

  public String getAllowType() {
    return allowType;
  }

  public void setAllowType(String allowType) {
    this.allowType = allowType;
  }

  public String getAllowValue() {
    return allowValue;
  }

  public void setAllowValue(String allowValue) {
    this.allowValue = allowValue;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }
}
