package com.chinasoft.it.wecode.security.authentication.api;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinasoft.it.wecode.common.util.NumberUtils;
import com.chinasoft.it.wecode.common.util.StringUtil;
import com.chinasoft.it.wecode.exception.AuthenticationException;
import com.chinasoft.it.wecode.security.UserPrincipal;
import com.chinasoft.it.wecode.security.dto.TokenResponseDto;
import com.chinasoft.it.wecode.security.spi.UserDetailService;
import com.chinasoft.it.wecode.security.utils.JwtEntity;
import com.chinasoft.it.wecode.security.utils.JwtUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * token 要去认证中心获取，而非到每个模块获取，集成该模块
 * @author Administrator
 *
 */
@Api("认证")
@RestController
@RequestMapping("/security/authentication")
public class AuthenticationApi {

  @Autowired(required = true)
  private UserDetailService userDetailService;

  @Autowired
  private HttpSession session;

  /**
   * SESSION KEY：验证码
   */
  private static final String K_VERIFYCODE = "$$USER_AUTHENTICATION_VERIFYCODE";

  /**
   * SESSION KEY：安全码
   */
  private static final String K_SECRETCODE = "$$USER_AUTHENTICATION_SECRETCODE";

  /**
   * 获取用户令牌
   * 
   * @param identifier 用户身份（可能是用户名，邮箱，电话等）
   * @param secret 安全码
   * @param secretType 安全码类型，默认是密码[pwd],手机验证码[sec]
   * @param verifyCode 验证码，默认可以不填写，当用户需要验证时才需要填写（通常一个用户密码输入错误几次后就需要验证）
   * @param expiryTimes 有效时间值，可以指定时间，也可以是 1:小时，2:天,3:周，4:月，other：实际有效时间
   * @return
   */
  @ApiOperation("获取token")
  @GetMapping("/token")
  public TokenResponseDto getToken(@RequestParam(value = "identifier") String identifier, @RequestParam(value = "secret") String secret,
      @RequestParam(value = "secretType", required = false, defaultValue = "pwd") String secretType,
      @RequestParam(value = "verifyCode", required = false) String verifyCode,
      @RequestParam(value = "expiryTimes", required = false, defaultValue = "1") Short expiryTimes) {

    String sessionVerifyCode = (String) session.getAttribute(K_VERIFYCODE);
    if (!StringUtil.isEmpty(sessionVerifyCode)) {
      if (StringUtil.isEmpty(verifyCode)) {
        throw new AuthenticationException("验证码不能为空");
      }
      // 当验证码校验失败时，重新生成验证码
      if (!sessionVerifyCode.equalsIgnoreCase(verifyCode)) {
        sessionVerifyCode = RandomStringUtils.randomAlphanumeric(5);
        session.setAttribute(K_VERIFYCODE, sessionVerifyCode);
        throw new AuthenticationException("验证码不正确");
      }
    }

    // TODO:待实现安全码生成逻辑
    if ("sec".equalsIgnoreCase(secretType)) {
      if (StringUtils.isEmpty(secret)) {
        throw new AuthenticationException("认证安全码不能为空");
      }

      // 检查是否用户安全码，安全码格式code$$expriteTimes
      String sessionSecretBody = (String) session.getAttribute(K_SECRETCODE);
      if (StringUtils.isEmpty(sessionSecretBody)) {
        throw new AuthenticationException("系统安全码未生成，请生成后重试");
      }

      String[] secretCodeInfo = sessionSecretBody.split("$$");
      if (secretCodeInfo.length != 2) {
        throw new AuthenticationException("系统安全码生成无效");
      }
      // 当安全码一致时，则校验是否过期
      if (secret.equals(secretCodeInfo[0].trim())) {
        int expir = NumberUtils.tryParse(secretCodeInfo[1], -1);
        if (expir == -1) {
          throw new AuthenticationException("系统安全码生成无效");
        }
        // 系统时间大于安全码有效时间(安全码时间是120秒)
        if (System.currentTimeMillis() > expir) {
          throw new AuthenticationException("安全码已经失效，请重新生成");
        }
      }

      return buildTokenResponseDto(identifier, expiryTimes);
    }

    UserPrincipal user = userDetailService.userDetails(identifier, secret);
    if (user == null) {
      throw new AuthenticationException("不存在的用户");
    }

    return buildTokenResponseDto(identifier, expiryTimes);
  }

  /**
   * TOKEN刷新
   * 用于前端定时调用并重置到当前前端存储，以便防止token过期后中断正在访问系统的用户的操作
   * @param token
   * @return
   */
  @ApiOperation("刷新token")
  @GetMapping("/token/refresh")
  public TokenResponseDto refreshToken(@RequestParam("token") String token) {
    JwtEntity jwtEntity = JwtUtil.parse(token);
    return buildTokenResponseDto(jwtEntity.getUid(), jwtEntity.effectiveTimes());
  }

  @ApiOperation(value = "刷新token认证签名", notes = "刷新后将失效所有基于jwt的Token(超级管理员才能操作)")
  @GetMapping("/token/refreshSignKey")
  public void refreshTokenSignKey() {
    JwtUtil.refreshSignKey();
  }

  private static final long HOUR = 1000 * 60 * 60;
  private static final long DAY = HOUR * 24;
  private static final long WEEK = DAY * 7;
  private static final long MONTH = WEEK * 4;
  private static final long[] EXPIRY_RANGE = new long[] {999999999, HOUR, DAY, WEEK, MONTH};

  private TokenResponseDto buildTokenResponseDto(String identifier, long expiryTimes) {
    if (expiryTimes < 1) {
      throw new IllegalArgumentException("非法的有效时间设置");
    }

    long expired = System.currentTimeMillis() + (expiryTimes > 4 ? expiryTimes : EXPIRY_RANGE[(int) expiryTimes]);
    String token = JwtUtil.get(identifier, null, expired);
    return new TokenResponseDto(token, identifier, expired);
  }

}
