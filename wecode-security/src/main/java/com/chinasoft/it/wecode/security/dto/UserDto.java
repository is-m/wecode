package com.chinasoft.it.wecode.security.dto;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.chinasoft.it.wecode.common.dto.BaseDto;
import com.chinasoft.it.wecode.common.validation.groups.Create;
import com.chinasoft.it.wecode.common.validation.groups.Query;
import com.chinasoft.it.wecode.common.validation.groups.Update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户编辑DTO")
@GroupSequence({Create.class, Update.class, Query.class, UserDto.class}) // 校验顺序，这里添加UserDto.是校验Defult.class顺序
public class UserDto extends BaseDto {

  private static final long serialVersionUID = 1703276127750258081L;

  /**
   * 姓名
   */
  @ApiModelProperty(name = "name", notes = "姓名/username", example = "liaoxianmu")
  @NotBlank
  @Length(max = 100)
  private String name;

  /**
   * 密码
   */
  @ApiModelProperty(name = "password", notes = "密码", example = "123456")
  @Length(min = 3, max = 100,message="密码长度不能小于{min}以及大于{max}")
  @NotBlank(groups = {Create.class},message="密码不能为空")
  private String password;

  /**
   * 备注
   */
  @ApiModelProperty(name = "remark", notes = "备注", example = "good boys")
  @Length(max = 1000)
  private String remark;

  /**
   * 电子邮箱
   */
  @ApiModelProperty(name = "mail", notes = "电子邮箱", example = "191625131@qq.com")
  @Length(max = 100)
  private String mail;

  /**
   * 移动号码 MP MobilePhone
   */
  @ApiModelProperty(name = "mobilePhone", notes = "电子邮箱", example = "18680382501")
  @Length(max = 20)
  private String mobilePhone;

  /**
   * 状态，0：失效，1：生效
   */
  @ApiModelProperty(name = "status", notes = "状态，0：失效，1生效", example = "1")
  @Range(min=0,max=1)
  private Integer status;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

}
