package com.chinasoft.it.wecode.security.utils;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;

import com.chinasoft.it.wecode.common.util.CollectionUtils;
import com.chinasoft.it.wecode.common.util.LogUtils;
import com.chinasoft.it.wecode.common.util.RandomUtl;
import com.chinasoft.it.wecode.common.util.StringUtil;
import com.chinasoft.it.wecode.exception.AuthenticationException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/**
 * JWT 工具类
 * @author Administrator
 *
 */
public class JwtUtil {

  private static String KEY_SECRET = StringUtil.random(10);

  public static final String CLAIM_SUB = "sub";

  public static final String CLAIM_IAT = "iat";

  public static final String CLAIM_EXP = "exp";

  // sub : subject , iat : create time , exp: expired time
  private static final Collection<String> innerKeys = Arrays.asList(CLAIM_SUB, CLAIM_IAT, CLAIM_EXP);

  private static final long EXPIRED_TIMES = 1000 * 60 * 60;

  private static final boolean base64Token = true;

  @SuppressWarnings("unused")
  private static final Logger log = LogUtils.getLogger();

  public static String get(String uid, Map<String, Object> claims) {
    return get(uid, claims, System.currentTimeMillis() + EXPIRED_TIMES);
  }

  /**
   * 获取TOKEN
   * @param uid
   * @param claims
   * @return
   */
  public static String get(String uid, Map<String, Object> claims, long expiredTimes) {
    if (claims != null && !claims.isEmpty()) {
      List<String> collect = claims.keySet().parallelStream().filter(innerKeys::contains).collect(Collectors.toList());
      Assert.isTrue(collect.size() == 0, "参数 claims 错误， 存在关键参数 " + collect);
    }


    // 为访客时延长过期时间
    Date expiration = new Date("$guest$".equals(uid) ? 9999999999999L : expiredTimes);
    String token = Jwts.builder().setSubject(uid) // 用户
        .addClaims(claims) // payload
        .setIssuedAt(new Date()) // 签发时间
        .setExpiration(expiration).signWith(SignatureAlgorithm.HS256, KEY_SECRET) // 签名算法
        .compact();

    return base64Token ? Base64Utils.encodeToString(token.getBytes()) : token;
  }

  /**
   * 解析TOKEN
   * @param token
   * @return
   */
  public static JwtEntity parse(String token) throws AuthenticationException {
    String realToken = base64Token ? new String(Base64Utils.decodeFromString(token)) : token;
    try {
      Map<String, Object> result = Jwts.parser().setSigningKey(KEY_SECRET).parseClaimsJws(realToken).getBody();
      return new JwtEntity(result);
    } catch (SignatureException e) {
      // SignatureException 密钥不正确
      throw new AuthenticationException("密钥不正确", e);
    } catch (ExpiredJwtException e) {
      // ExpiredJwtException 过期
      throw new AuthenticationException("用户TOKEN已经过期", e);
    } catch (Exception e) {
      throw new AuthenticationException("未知异常", e);
    }
  }

  public static boolean valid(String token) {
    return parse(token) == null;
  }

  /**
   * 刷新token认证签名,刷新后将失效所有基于jwt的Token
   */
  public static void refreshSignKey() {
    KEY_SECRET = StringUtil.random(10);
  }

  public static void main(String[] args) {
    String string = get("$guest$", CollectionUtils.newMap("123", "123"));
    System.out.println(string);
    System.out.println(Base64.getEncoder().encodeToString(string.getBytes()));
    System.out.println(parse(string));
  }
}
