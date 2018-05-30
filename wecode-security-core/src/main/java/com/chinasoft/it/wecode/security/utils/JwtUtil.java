package com.chinasoft.it.wecode.security.utils;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.springframework.util.Assert;

import com.chinasoft.it.wecode.common.util.LogUtils;
import com.chinasoft.it.wecode.common.util.StringUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

public class JwtUtil {

	private static final String KEY_SECRET = StringUtil.random(10);

	private static final Collection<String> innerKeys = Arrays.asList("sub", "iat", "exp");

	private static final long expiredTimes = 1000 * 60 * 10;

	@SuppressWarnings("unused")
	private static final Logger log = LogUtils.getLogger();

	public static String get(String uid, Map<String, Object> claims) {
		if (claims != null && !claims.isEmpty()) {
			List<String> collect = claims.keySet().stream().filter(item -> innerKeys.contains(item))
					.collect(Collectors.toList());
			Assert.isTrue(collect.size() == 0, "参数 claims 错误， 存在关键参数 " + ToStringBuilder.reflectionToString(collect));
		}

		return Jwts.builder().setSubject(uid) // 用户
				.addClaims(claims) // payload
				.setIssuedAt(new Date()) // 签发时间
				.setExpiration(new Date(System.currentTimeMillis() + expiredTimes))
				.signWith(SignatureAlgorithm.HS256, KEY_SECRET) // 签名算法
				.compact();
	}

	public static Map<String, Object> parse(String token) {
		try {
			return Jwts.parser().setSigningKey(KEY_SECRET).parseClaimsJws(token).getBody();
		} catch (SignatureException | ExpiredJwtException e) {
			// SignatureException 密钥不正确
			// ExpiredJwtException 过期
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean valid(String token) {
		return parse(token) == null;
	}

	public static void main(String[] args) {
		String string = get("$guest$", new HashMap<>());
		System.out.println(string);
		System.out.println(Base64.getEncoder().encodeToString(string.getBytes()));
		System.out.println(parse(string));
	}
}
