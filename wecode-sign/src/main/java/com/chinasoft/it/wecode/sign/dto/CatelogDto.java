package com.chinasoft.it.wecode.sign.dto;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.chinasoft.it.wecode.common.dto.BaseDto;
import com.chinasoft.it.wecode.common.validation.annotation.Enumerator;

import io.swagger.annotations.ApiModelProperty;

public class CatelogDto extends BaseDto {

  private static final long serialVersionUID = -1727311751417200055L;

  /**
   * 父节点ID
   */
  @ApiModelProperty(value = "父节点ID", example = "root")
  @Length(max = 36)
  private String pid;

  /**
   * 栏目名称
   */
  @ApiModelProperty(value = "栏目名称", example = "")
  @NotEmpty
  @Length(min = 1, max = 100)
  private String name;

  /**
   * 栏目短路径
   */
  @ApiModelProperty(value = "访问地址", notes = "不需要以/作为起始符,(如果使用/作为起始符，并且系统使用的是Antd Pro框架则可能会导致栏目无法正确定位)", example = "")
  @Length(max = 256)
  private String uri;

  /**
   * 图标
   */
  @ApiModelProperty(value = "图标", example = "user")
  @Length(max = 50)
  private String icon;

  /**
   * 顺序
   */
  @ApiModelProperty(value = "顺序", example = "0")
  @Range(min = 0, max = 10000)
  private Integer seq;

  /**
   * 状态：0 失效，1 生效
   */
  @ApiModelProperty(value = "状态", notes = "1:生效，0：失效", example = "1")
  @Range(min = 0, max = 1)
  private Integer status;

  /**
   * 允许访问类型，all:全部可见，children:有子栏目时可见,authority:有权限时可见
   */
  @ApiModelProperty(value = "状态", notes = "all:全部可见，children:有子栏目时可见,authority:有权限时可见", example = "all")
  @NotEmpty
  @Enumerator({"all", "children", "authority"})
  private String allowType;

  /**
   * 允许访问值，在访问类型为有权限可见时应该需要设置该值,通常用于绑定功能权限
   */
  @ApiModelProperty(value = "允许访问值", notes = "在访问类型为有权限可见时应该需要设置该值,通常用于绑定功能权限", example = "")
  private String allowValue;

  /**
   * 目标类型，page:站内跳转(默认)，window：新页面
   */
  @ApiModelProperty(value = "目标类型", notes = "page:站内跳转(默认)，window：新页面", example = "page")
  @NotEmpty
  @Enumerator({"page", "iframe", "window"})
  private String target;

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

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
