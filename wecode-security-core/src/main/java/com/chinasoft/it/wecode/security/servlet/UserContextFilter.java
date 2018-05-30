package com.chinasoft.it.wecode.security.servlet;

import java.io.IOException;
import java.util.Base64;

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

public class UserContextFilter extends OncePerRequestFilter {

	private static final Logger log = LogUtils.getLogger();

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws ServletException, IOException {

		String token = req.getHeader(TokenConstants.HEAD_AUTHENTICATION);

		if (!StringUtils.isEmpty(token)) {
			String decodeToken = new String(Base64.getDecoder().decode(token));
			log.info("filter user context of token " + decodeToken);

			TokenService bean = ApplicationUtils.getBean(TokenService.class);
			TokenContext tokenContext = bean.parse(decodeToken);
			if (tokenContext != null) {
				log.info("tokenContext found of {}", tokenContext);
				UserContextManager.set(tokenContext.getUid());
			} else {
				log.warn("token is faild");
			}
		}

		try {
			chain.doFilter(req, resp);
		} finally {
			UserContextManager.remove();
		}
	}

}
