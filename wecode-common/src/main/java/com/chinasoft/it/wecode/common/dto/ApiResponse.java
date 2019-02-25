package com.chinasoft.it.wecode.bean;

/**
 * API 结果
 * @author Administrator
 *
 */
public class ApiResponse {

  /**
   * 响应码
   */
  private String code;

  /**
   * 消息
   */
  private String msg;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public ApiResponse(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public ApiResponse() {}

}
