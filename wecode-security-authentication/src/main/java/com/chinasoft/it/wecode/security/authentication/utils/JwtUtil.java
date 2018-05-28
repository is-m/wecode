package com.chinasoft.it.wecode.security.authentication.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.chinasoft.it.wecode.common.util.StringUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

public class JwtUtil {

	private static final String KEY_SECRET = StringUtil.random(10);

	public static String get(String userIdentifer, Map<String, Object> claims) {
		return Jwts.builder().setSubject(userIdentifer) // 用户
				.addClaims(claims) // payload
				.setIssuedAt(new Date()) // 签发时间
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
		String string = get("122131233", new HashMap<>());
		System.out.println(string);
		System.out.println(parse(string));
	}
}
