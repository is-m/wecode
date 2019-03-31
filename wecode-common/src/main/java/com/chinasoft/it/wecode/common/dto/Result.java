package com.chinasoft.it.wecode.common.dto;

public class Result {
  /**
   * 响应码
   */
  private String code;

  /**
   * 消息
   */
  private String msg;

  /**
   * 数据
   */
  private Object data;

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

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public Result() {}

  public Result(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public Result(String code, String msg, Object data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public static ApiResponse of(Object code, String msg) {
    return new ApiResponse(String.valueOf(code), msg);
  }

  public static ApiResponse of(Object code, String msg, Object data) {
    return new ApiResponse(String.valueOf(code), msg, data);
  }



}
