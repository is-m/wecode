package com.chinasoft.it.wecode.security.spi;

import com.chinasoft.it.wecode.exception.AuthenticationException;
import com.chinasoft.it.wecode.security.UserPrincipal;

/**
 * 用户详情服务
 * @author Administrator
 *
 */
public interface UserDetailService {

  /**
   * 获取用户详情
   * 
   * @param identifier 用户身份，可能是用户名/电话/邮箱
   * @param secret 安全码，可能是密码/手机短信认证码
   * @return
   * @throws AuthenticationException 返回异常可以是，用户名不存在，密码错误，用户过期/锁定
   */
  public UserPrincipal userDetails(String identifier, String secret) throws AuthenticationException;

}
