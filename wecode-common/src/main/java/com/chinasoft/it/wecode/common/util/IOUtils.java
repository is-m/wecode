package com.chinasoft.it.wecode.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;

public class IOUtils {
  /**
   * 通过BufferedReader和字符编码集转换成byte数组
   * @param br
   * @param encoding
   * @return
   * @throws IOException
   */
  public byte[] readBytes(BufferedReader br, String encoding) throws IOException {
    String str = null, retStr = "";
    while ((str = br.readLine()) != null) {
      retStr += str;
    }
    if (StringUtils.isNotBlank(retStr)) {
      return retStr.getBytes(Charset.forName(encoding));
    }
    return null;
  }
}
