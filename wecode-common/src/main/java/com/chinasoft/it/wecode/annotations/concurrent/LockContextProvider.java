package com.chinasoft.it.wecode.annotations.concurrent;

import java.util.Map;

public interface LockContextProvider {

  /**
   * 初始化前的准备（在当前主线程中运行），用于提前获取主线程的线程变量，触发init方法时会使用该参数
   * @return 返回准备透传到新线程的数据
   */
  public Map<String, Object> getExtra();

  /**
   * 初始化当前线程上下文，
   * 
   * 在调用这个方法前需要提前准备好需要设置的内容，在后在该方法内进行填充，例如：
   *{@code
   *    final SecurityContext sc = SecurityContext.get();
   *    new LockContextProvider(){
   *      init(){
   *         SecurityContext.set(sc);
   *      }
   *    }
   *} 
   */
  public void init(Map<String, Object> extra);

  /**
   * 删除在当前线程上下文设置的内容
   */
  public void destroy();
}
