package com.chinasoft.it.wecode.common.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 随机函数
 * @author Administrator
 *
 */
public class RandomUtl {

  /**
   * 获取随机数字字符串 
   * @return
   */
  public static String numeric(int count) throws IllegalArgumentException {
    Assert.isTrue(count > 0, "digitCapacity 必须大于0");
    return RandomStringUtils.randomNumeric(count);
  }

  /**
   * 字母数值混合
   * @return
   */
  public static String mix(int count) {
    Assert.isTrue(count > 0, "digitCapacity 必须大于0");
    return RandomStringUtils.randomAlphanumeric(count);
  }

  /**
   * 生成从ASCII 32到126组成的随机字符串 ，即包含 英文，数字，符号的字符串
   * @param count
   * @return
   */
  public static String ascii(int count) {
    return RandomStringUtils.randomAscii(4);
  }

}
