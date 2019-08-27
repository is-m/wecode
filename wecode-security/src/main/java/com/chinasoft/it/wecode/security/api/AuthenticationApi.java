package com.chinasoft.it.wecode.security.api;

import com.chinasoft.it.wecode.annotations.security.MoneySecurity;
import com.chinasoft.it.wecode.common.util.*;
import com.chinasoft.it.wecode.exception.AuthenticationException;
import com.chinasoft.it.wecode.exception.PasswordInvalidException;
import com.chinasoft.it.wecode.security.dto.TokenResponseDto;
import com.chinasoft.it.wecode.security.spi.UserDetailService;
import com.chinasoft.it.wecode.security.utils.JwtEntity;
import com.chinasoft.it.wecode.security.utils.JwtUtil;
import com.google.common.base.Objects;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * token 要去认证中心获取，而非到每个模块获取，集成该模块
 *
 * FIXME:该类有较多的用到session和application需要重点评估合理性
 *
 * @author Administrator
 */
@Api("认证")
@RestController
@RequestMapping("/security/authentication")
public class AuthenticationApi {

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private HttpSession session;

    private Map<String, Object> application = ApplicationUtils.getApplication();

    /**
     * SESSION KEY：验证码
     */
    private static final String K_VERIFYCODE = "$$USER_AUTHENTICATION_VERIFYCODE";

    /**
     * SESSION KEY：安全码
     */
    private static final String K_SECRETCODE = "$$USER_AUTHENTICATION_SECRETCODE";

    /**
     * SESSION KEY：密码失败次数
     */
    private static final String K_INVALID_PASSWORD_COUNT = "$$USER_AUTHENTICATION_CNT_INVALID_PWD_";

    /**
     * 在限定时间内最大密码失败次数，默认 5 次
     * TODO:这个最后要放到数据字典中进行可配置 System.Security.Login.InvalidPassword.maxCount
     */
    @Value("${System.Security.Login.InvalidPassword.maxCount:5}")
    private int CONF_INVALID_PASSWORD_MAX_COUNT;

    /**
     * 限定密码失败次数的时间，默认 300 秒
     * TODO:这个最后要放到数据字典中进行可配置 System.Security.Login.InvalidPassword.interval
     */
    @Value("${System.Security.Login.InvalidPassword.interval:300}")
    private int CONF_INVALID_PASSWORD_INTERVAL;

    /**
     * 密码失败次数超过允许的次数后，可重新登陆的时间，默认 300 秒
     * TODO:这个最后要放到数据字典中进行可配置 System.Security.Login.InvalidPassword.reLoginTime
     */
    @Value("${System.Security.Login.InvalidPassword.reLoginTime:300}")
    private int CONF_INVALID_PASSWORD_RE_LOGIN_TIME;

    /**
     * 获取安全码
     *
     * @return
     */
    @ApiOperation("获取手机安全码（短信通知）")
    @MoneySecurity
    @GetMapping("/mobileSecurityCode")
    public void getPhoneSecurityCode(String phoneNumber) {
        String key = "$$USER_MOBILE_SECURITY_CODE_" + phoneNumber;
        Object securityCodeString = application.get(key);
        if (securityCodeString != null && !StringUtils.isEmpty(securityCodeString.toString())) {
            String[] securityCodeArray = securityCodeString.toString().split("$$");
            long securityCodeSendTime = Long.valueOf(securityCodeArray[1]);
            long timeDifference = System.currentTimeMillis() - securityCodeSendTime;
            Assert.isTrue(timeDifference > MINITUS, String.format("请在 %s 秒后再调用接口", timeDifference));
        }
        // 校验手机号码
        int securityCodeRandom = RandomUtils.nextInt(10000, 99999);
        application.put(key, securityCodeRandom + "$$" + System.currentTimeMillis());
        // TODO:短信通知手机安全码
        // smsServcice.send("Template_String_Id",securityCodeRandom);
    }


    /**
     * 获取用户令牌
     *
     * @param identifier  用户身份（可能是用户名，邮箱，电话等）
     * @param secret      安全码
     * @param secretType  安全码类型，默认是密码[pwd],手机验证码[sec]
     * @param verifyCode  验证码，默认可以不填写，当用户需要验证时才需要填写（通常一个用户密码输入错误几次后就需要验证）
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

        this.loginCheck(identifier, secret);

        return buildTokenResponseDto(identifier, expiryTimes);
    }

    private void loginCheck(String identifier, String secret) {
        // 如果时密码不正确时，则设置密码失败次数 invalidPasswordDesc 的格式为   次数;最后一次错误时间
        // FIXME：如果可以需要将失败的信息回写到Cookie中，前端先做校验，用来减少可能的网络请求
        String invalidPasswordKey = K_INVALID_PASSWORD_COUNT + identifier;
        Object invalidPasswordDesc = application.get(invalidPasswordKey);
        int invalidCount = -1;      // 失败次数
        long lastInvalidTime = -1;  // 最后失败时间

        if (invalidPasswordDesc != null) {
            String[] invalidArray = invalidPasswordDesc.toString().split(";");
            invalidCount = Integer.valueOf(invalidArray[0]);
            lastInvalidTime = Long.valueOf(invalidArray[1]);


            // 当登录失败次数大于允许的次数时，则检查超时是否超时
            if (invalidCount >= CONF_INVALID_PASSWORD_MAX_COUNT) {
                long timeout = (System.currentTimeMillis() - lastInvalidTime) / 1000;
                // 当禁止登陆的时间小于系统重新登陆的时间，则通知剩余时间
                if(timeout < CONF_INVALID_PASSWORD_RE_LOGIN_TIME) {
                    long retryInterval = CONF_INVALID_PASSWORD_RE_LOGIN_TIME - timeout;
                    throw new AuthenticationException(String.format("密码错误超过允许的次数 %s, 请在 %s 秒后重试", CONF_INVALID_PASSWORD_MAX_COUNT, retryInterval));
                }else{
                    // 当禁止登陆的时间大于系统重新登陆的时间，则清理禁止登陆的数据，以便允许用户登陆
                    application.remove(invalidPasswordKey);
                    invalidPasswordDesc = null;
                }
            }
        }

        try {
            userDetailService.userDetails(identifier, secret);
            // 如果登陆失败的检查存在，且登陆成功，则删除失败检查的数据
            if (invalidPasswordDesc != null) {
                application.remove(invalidPasswordKey);
            }
        } catch (PasswordInvalidException e) {
            long currentTime = System.currentTimeMillis();

            // 第一次失败时初始化失败
            int continueInvalidPassword = 1;
            if (invalidPasswordDesc == null) {
                application.put(invalidPasswordKey, "1;" + currentTime);
            } else {
                // 如果最后一次记录时间大于间隔时间，则重置失败次数
                if (((currentTime - lastInvalidTime) / 1000) > CONF_INVALID_PASSWORD_INTERVAL) {
                    application.put(invalidPasswordKey, "1;" + currentTime);
                } else {
                    // 否则累加失败次数，并更新最后失败时间
                    continueInvalidPassword =  invalidCount + 1;
                    application.put(invalidPasswordKey, continueInvalidPassword + ";" + currentTime);
                }
            }

            double invalidRate = ((double)continueInvalidPassword / (double)CONF_INVALID_PASSWORD_MAX_COUNT ) * 100;
            // 小于失败的总次数的70%时，不提醒用户剩余次数
            if(invalidRate < 70){
                throw e;
            }else{
                String errorMsg;
                if(invalidRate >= 100){
                    errorMsg = String.format("密码错误已经超过最大允许次数 %s，当前登陆账号5分钟后才能再次进行登陆",CONF_INVALID_PASSWORD_MAX_COUNT);
                }else{
                    errorMsg = String.format("密码错误，再错误 %s 次，该账号将会被暂停登陆5分钟",CONF_INVALID_PASSWORD_MAX_COUNT - continueInvalidPassword);
                }
                throw new AuthenticationException(errorMsg,e);
            }
        }
    }

    /**
     * TOKEN刷新
     * 用于前端定时调用并重置到当前前端存储，以便防止token过期后中断正在访问系统的用户的操作
     *
     * @param token
     * @return
     */
    @ApiOperation("刷新token")
    @GetMapping("/token/refresh")
    public TokenResponseDto refreshToken(@RequestParam("token") String token) {
        JwtEntity jwtEntity = JwtUtil.parse(token);
        Map<String, Object> payload = jwtEntity.getPayload();
        if (!payload.containsKey("clientIp") || !Objects.equal(getRequestIp(), payload.get("clientIp"))) {
            throw new AuthenticationException("认证失败");
        }
        return buildTokenResponseDto(jwtEntity.getUid(), jwtEntity.effectiveTimes());
    }

    @ApiOperation(value = "刷新token认证签名", notes = "刷新后将失效所有基于jwt的Token(超级管理员才能操作)")
    @GetMapping("/token/refreshSignKey")
    public void refreshTokenSignKey() {
        JwtUtil.refreshSignKey();
    }

    private static final long MINITUS = 1000 * 60;
    private static final long HOUR = MINITUS * 60;
    private static final long DAY = HOUR * 24;
    private static final long WEEK = DAY * 7;
    private static final long MONTH = WEEK * 4;
    private static final long[] EXPIRY_RANGE = new long[]{999999999, HOUR, DAY, WEEK, MONTH};

    /**
     * 获取响应
     * FIXME：需要考虑是否依赖 客户端IP来再强化一次TOKEN的有效范围
     *
     * @param identifier
     * @param expiryTimes
     * @return
     */
    private TokenResponseDto buildTokenResponseDto(String identifier, long expiryTimes) {
        if (expiryTimes < 1) {
            throw new IllegalArgumentException("非法的有效时间设置");
        }

        long expired = System.currentTimeMillis() + (expiryTimes > 4 ? expiryTimes : EXPIRY_RANGE[(int) expiryTimes]);
        Map<String, Object> payload = CollectionUtils.newMap("clientIp", getRequestIp());
        String token = JwtUtil.get(identifier, payload, expired);
        return new TokenResponseDto(token, identifier, expired);
    }

    private String getRequestIp() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return ServletUtils.getRemoteAddr(sra.getRequest());
    }

}
