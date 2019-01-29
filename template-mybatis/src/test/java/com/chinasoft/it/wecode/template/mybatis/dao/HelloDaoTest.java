package com.chinasoft.it.wecode.template.mybatis.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chinasoft.it.wecode.template.mybatis.MyBatisApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MyBatisApplication.class}) // 指定启动类
public class HelloDaoTest {

  @Autowired
  private HelloDao dao;
  
  @Test
  public void getDbTime() {
    System.out.println(dao.getDbTime());
  }

}
