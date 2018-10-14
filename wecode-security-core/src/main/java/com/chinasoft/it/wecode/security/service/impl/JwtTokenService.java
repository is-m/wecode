package com.chinasoft.it.wecode.security.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.chinasoft.it.wecode.common.util.ApplicationUtils;
import com.chinasoft.it.wecode.exception.AuthenticationException;
import com.chinasoft.it.wecode.security.TokenContext;
import com.chinasoft.it.wecode.security.service.TokenManagementService;
import com.chinasoft.it.wecode.security.service.TokenService;
import com.chinasoft.it.wecode.security.utils.JwtUtil;

/**
 * JWT Token 服务
 * 
 * @author Administrator
 *
 */
@Service
public class JwtTokenService implements TokenService {

  @Override
  public String build(TokenContext context) {
    String token = JwtUtil.get(context.getUid(), context.getPayload());
    TokenManagementService managementService = ApplicationUtils.getBean(TokenManagementService.class);
    // 调用保存token的接口
    if (managementService != null) {
      managementService.save(context.getUid(), token);
    }
    return token;
  }

  @Override
  public TokenContext parse(String token) {
    Map<String, Object> payload = JwtUtil.parse(token);
    if (payload == null) {
      return null;
    }

    String uid = null;
    Long created = null;
    Long expired = null;

    if (payload.containsKey("sub")) {
      uid = payload.remove("sub").toString();
    }
    if (payload.containsKey("iat")) {
      created = Long.valueOf(payload.remove("iat").toString());
    }
    if (payload.containsKey("exp")) {
      String exp = payload.remove("exp").toString();
      // jwt过期时间长度为10位数值
      expired = Long.valueOf(exp.length() == 10 ? exp + "000" : exp);
      if (expired < System.currentTimeMillis()) {
        throw new AuthenticationException("TOKEN已过期");
      }
    } else {
      // 未设置用户过期则表示TOKEN无效
      throw new AuthenticationException("TOKEN无效");
    }

    return new TokenContext(uid, payload, created, expired);
  }

}
