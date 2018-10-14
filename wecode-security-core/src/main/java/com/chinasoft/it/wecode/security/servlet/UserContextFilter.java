package com.chinasoft.it.wecode.security.servlet;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.chinasoft.it.wecode.common.util.ApplicationUtils;
import com.chinasoft.it.wecode.common.util.LogUtils;
import com.chinasoft.it.wecode.security.TokenContext;
import com.chinasoft.it.wecode.security.service.TokenService;
import com.chinasoft.it.wecode.security.service.impl.UserContextManager;
import com.chinasoft.it.wecode.security.utils.TokenConstants;

/**
 * 用户上下文过滤器
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
    if (uri.lastIndexOf(".") > 0) {
      String exten = uri.substring(uri.lastIndexOf(".") + 1, uri.length());
      // 静态资源不检查token
      if (RESOURCE_FILTER.contains(exten)) {
        chain.doFilter(req, resp);
        return;
      }
    }

    String token = req.getHeader(TokenConstants.HEAD_AUTHENTICATION);
    // 存在token时才识别token内容
    if (!StringUtils.isEmpty(token)) {
      log.info("filter user context of token " + token);

      TokenService bean = ApplicationUtils.getBean(TokenService.class);
      TokenContext tokenContext = bean.parse(token);
      if (tokenContext != null) {
        log.info("tokenContext found of {}", tokenContext);
        UserContextManager.set(tokenContext.getUid());
      } else {
        log.warn("token is faild");
      }

      try {
        chain.doFilter(req, resp);
      } finally {
        UserContextManager.remove();
      }
    } else {
      chain.doFilter(req, resp);
    }
  }

}
