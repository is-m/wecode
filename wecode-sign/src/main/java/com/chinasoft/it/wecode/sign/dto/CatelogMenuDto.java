package com.chinasoft.it.wecode.sign.dto;

import java.util.List;

import com.chinasoft.it.wecode.common.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CatelogMenuDto extends BaseDto {

  private static final long serialVersionUID = 349857223109032278L;

  /**
   * 栏目ID
   */
  private String id;

  /**
   * 栏目名称
   */
  private String name;

  /**
   * uri
   */
  private String uri;

  /**
   * 图标
   */
  private String icon;

  /**
   * 子栏目
   */
  private List<CatelogMenuDto> children;

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

  public List<CatelogMenuDto> getChildren() {
    return children;
  }

  public void setChildren(List<CatelogMenuDto> children) {
    this.children = children;
  }

  public CatelogMenuDto() {}

  public CatelogMenuDto(String name, String uri, String icon) {
    this.name = name;
    this.uri = uri;
    this.icon = icon;
  }

  public CatelogMenuDto(CatelogResultDto dto) {
    this(dto.getName(), dto.getUri(), dto.getIcon());
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
