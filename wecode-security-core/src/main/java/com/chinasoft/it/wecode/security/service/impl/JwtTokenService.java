package com.chinasoft.it.wecode.security.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.chinasoft.it.wecode.common.util.ApplicationUtils;
import com.chinasoft.it.wecode.security.AuthenticationException;
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
			expired = Long.valueOf(payload.remove("exp").toString());
			if (expired < System.currentTimeMillis()) {
				throw new AuthenticationException("token 已经过期");
			}
		} else {
			throw new AuthenticationException("token中未发现过期信息");
		}

		return new TokenContext(uid, payload, created, expired);
	}

}
