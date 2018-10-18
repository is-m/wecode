package com.chinasoft.it.wecode.security.servlet;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.chinasoft.it.wecode.common.util.LogUtils;
import com.chinasoft.it.wecode.common.util.NumberUtils;
import com.chinasoft.it.wecode.common.util.ServletUtils;
import com.chinasoft.it.wecode.exception.AuthenticationException;
import com.chinasoft.it.wecode.security.service.impl.UserContextManager;
import com.chinasoft.it.wecode.security.utils.JwtEntity;
import com.chinasoft.it.wecode.security.utils.JwtUtil;
import com.chinasoft.it.wecode.security.utils.TokenConstants;

/**
 * 用户上下文过滤器
 * 
 * 用户识别存在以下几种方式
 * 1.JWT 用户TOKEN
 * 2.分布式会话(redis,spring-boot-session-start) 获取当前用户信息的json并parseTo
 * 3. 用户ID + SECRET + TIMESTAMP 通过 SHA256 生成 SIGN ，服务端采用同种方式进行签名，通过后 检查TIMESTAMP 跟服务端的时间差进行有效性验证，都通过的情况下 再将当前 用户ID标记为当前请求对象并放入请求上下文
 * @author Administrator
 *
 */
public class UserContextFilter extends OncePerRequestFilter {

  private static final Logger log = LogUtils.getLogger();

  /**
   * 静态资源过滤
   */
  private static final String RESOURCE_FILTER = "html,js,jsp";

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {

    String uri = req.getRequestURI();
    int dotpos = uri.lastIndexOf(".");
    if (dotpos > 0) {
      String exten = uri.substring(dotpos + 1, uri.length());
      // 静态资源不检查token
      if (RESOURCE_FILTER.contains(exten)) {
        chain.doFilter(req, resp);
        return;
      }
    }

    String token = req.getHeader(TokenConstants.HEAD_AUTHENTICATION);
    // jwt token 认证
    if (!StringUtils.isEmpty(token)) {
      doTokenFilter(req, resp, chain, token);
      return;
    }

    // TODO：签名认证，通常用于应用级别的访问认证
    String sign = req.getHeader("X-AUTH-SIGN");
    if (!StringUtils.isEmpty(sign)) {
      String identifier = req.getHeader("X-AUTH-UID");
      int timestamp = NumberUtils.tryParse(req.getHeader("X-AUTH-TIME"), -1);
      Assert.hasText(identifier, "存在 sign  时，identifier 不能为空");
      // timestamp 不能为空
      Assert.isTrue(timestamp > 0, "存在 sign  时，timestamp 不正确");
      if (timestamp > System.currentTimeMillis()) {

      }

    } else {
      chain.doFilter(req, resp);
    }
  }

  private void doTokenFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain, String token) throws IOException, ServletException {
    log.info("filter user context of token " + token);

    JwtEntity jwtEntity = null;

    try {
      jwtEntity = JwtUtil.parse(token);
    } catch (AuthenticationException e) {
      ServletUtils.write(resp, e.getMessage(), e.getCode());
      e.printStackTrace();
      return;
    }

    if (jwtEntity != null) {
      log.info("jwtEntity found of {}", jwtEntity);
      UserContextManager.set(jwtEntity.getUid());
    } else {
      log.warn("token is faild");
    }

    try {
      chain.doFilter(req, resp);
    } finally {
      UserContextManager.remove();
    }
  }

}
