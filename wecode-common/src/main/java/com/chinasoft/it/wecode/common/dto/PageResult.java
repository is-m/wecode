package com.chinasoft.it.wecode.common.dto;

import java.util.List;

/**
 * 
 * 因为spring Pageable返回过多的无用字段，所以用该类进行封装
 * 
 * 即如果返回类型为Pageable，会默认返回该类型值
 * @author Administrator
 *
 */
public class PageResult<T> {

  /**
   * 页大小
   */
  private int size;

  /**
   * 总记录数
   */
  private int total;

  /**
   * 页数据
   */
  private List<T> data;

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }


}
