package com.chinasoft.it.wecode.sign.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.alibaba.druid.util.StringUtils;
import com.chinasoft.it.wecode.common.exception.ValidationException;
import com.chinasoft.it.wecode.common.util.DESUtil;
import com.chinasoft.it.wecode.common.util.DateUtils;
import com.chinasoft.it.wecode.common.util.LogUtils;
import com.chinasoft.it.wecode.common.util.MapperUtils;
import com.chinasoft.it.wecode.sign.domain.Sign;
import com.chinasoft.it.wecode.sign.dto.SignLogDto;
import com.chinasoft.it.wecode.sign.dto.SignResultDto;
import com.chinasoft.it.wecode.sign.mapper.SignMapper;
import com.chinasoft.it.wecode.sign.repository.SignRepository;
import com.chinasoft.it.wecode.sign.util.SignConstant;

@Service
public class SignService {

  private static final Logger logger = LogUtils.getLogger();

  @Autowired
  private SignRepository repo;

  @Autowired
  private SignMapper mapper;

  @Autowired
  private SignLogService signLogService;

  /**
   * sign
   * 
   * @param secretString
   * @return
   */
  public SignResultDto sign(String secretString) {
    String decryptString = null;
    try {
      decryptString = DESUtil.decryption(secretString, SignConstant.SIGN_DES_KEY);
    } catch (Exception e) {
      logger.warn("签到信息解密失败");
      throw new ValidationException("非法数据", e);
    }

    if (StringUtils.isEmpty(decryptString)) {
      logger.warn("签到空内容为空");
      throw new ValidationException("非法数据");
    }

    String[] splited = decryptString.split(SignConstant.SPLITTER);
    if (splited.length != 2) {
      logger.warn("签到内容格式不对");
      throw new ValidationException("非法数据");
    }

    String userId = splited[0];
    String point = splited[1];

    // TODO:计算打卡位置合法性,公司指定单个或多个低点辐射范围内
    // 获取基础数据中配置的合法打卡位置，

    Date current = new Date();
    Date dayStart = DateUtils.getDayStart(current);
    String signTime = SignConstant.FORMART_TIME.format(current);

    Sign daySign = repo.findOneByUserIdAndSignDate(userId, dayStart);
    // 当天还未签到时初始化当前用户天打卡记录
    if (daySign == null) {
      daySign = new Sign();
      daySign.setBeginTime(signTime);
      daySign.setBeginPoint(point);
      daySign.setEndTime(signTime);
      daySign.setEndPoint(point);
      daySign.setSignDate(dayStart);
      daySign.setUserId(userId);
    } else {
      // 已经签到则更新最后的打卡位置和时间
      daySign.setEndTime(signTime);
      daySign.setEndPoint(point);
    }

    // 记录打卡日志
    SignLogDto signLogDto = new SignLogDto(userId, current, point);
    signLogService.create(signLogDto);

    return mapper.from(repo.save(daySign));
  }

  private void validSignPoint(String signPoint) throws ValidationException {

  }

  public Page<SignResultDto> findMySignPagedList(Pageable pageable, String userId) {
    if (StringUtils.isEmpty(userId)) {
      throw new ValidationException("user id cannot be null or empty");
    }

    Sign condition = new Sign();
    condition.setUserId(userId);
    return repo.findAll(Example.of(condition), pageable).map(mapper::from);
  }
}
