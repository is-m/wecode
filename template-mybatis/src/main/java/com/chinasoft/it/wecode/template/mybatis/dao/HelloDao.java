package com.chinasoft.it.wecode.template.mybatis.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HelloDao {

  /**
   * 获取数据库时间
   * @return
   */
  @Select("select sysdate() from dual")
  Date getDbTime();
}
