package com.chinasoft.it.wecode.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;

/**
 * 
 * @author wenqi5
 * https://blog.csdn.net/wenqisun/article/details/51121460
 * 
 */
public class GZIPUtils {

  public static final String GZIP_ENCODE_UTF_8 = "UTF-8";

  public static final String GZIP_ENCODE_ISO_8859_1 = "ISO-8859-1";

  private static final Logger log = LogUtils.getLogger();

  /**
   * 
   * @param bytes
   * @return
   */
  public static String uncompressToString(byte[] bytes) {
    return uncompressToString(bytes, GZIP_ENCODE_UTF_8);
  }

  /**
   * 
   * @param bytes
   * @param encoding
   * @return
   */
  public static String uncompressToString(byte[] bytes, String encoding) {
    if (bytes == null || bytes.length == 0) {
      return null;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ByteArrayInputStream in = new ByteArrayInputStream(bytes);
    try {
      GZIPInputStream ungzip = new GZIPInputStream(in);
      byte[] buffer = new byte[256];
      int n;
      while ((n = ungzip.read(buffer)) >= 0) {
        out.write(buffer, 0, n);
      }
      return out.toString(encoding);
    } catch (IOException e) {
      log.error("gzip uncompress to string error.", e);
    }
    return null;
  }

  /**
   * @param str：正常的字符串
   * @return 压缩字符串 类型为：  ³)°K,NIc i£_`Çe#  c¦%ÂXHòjyIÅÖ`
   * @throws IOException
   */
  public static String compress(String str) throws IOException {
    if (str == null || str.length() == 0) {
      return str;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    GZIPOutputStream gzip = new GZIPOutputStream(out);
    gzip.write(str.getBytes());
    gzip.close();
    return out.toString("ISO-8859-1");
  }


  /**
   * @param str：类型为：  ³)°K,NIc i£_`Çe#  c¦%ÂXHòjyIÅÖ`
   * @return 解压字符串  生成正常字符串。
   * @throws IOException
   */
  public static String uncompress(String str) throws IOException {
    if (str == null || str.length() == 0) {
      return str;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
    GZIPInputStream gunzip = new GZIPInputStream(in);
    byte[] buffer = new byte[256];
    int n;
    while ((n = gunzip.read(buffer)) >= 0) {
      out.write(buffer, 0, n);
    }
    // toString()使用平台默认编码，也可以显式的指定如toString("GBK")
    return out.toString();
  }

  /**
   * 解压decode过的数据
   * @param val
   * @return
   * @throws IOException
   */
  public static String uncompressURI(String val) throws IOException {
    return uncompressURI(val, false);
  }

  /**
   * 解压Decoder过的数据
   * @param val
   * @param isBeforeDecoder
   * @return
   * @throws IOException
   */
  public static String uncompressURI(String val, boolean isBeforeDecoder) throws IOException {
    return isBeforeDecoder ? uncompress(URLDecoder.decode(val, "UTF-8")) : URLDecoder.decode(uncompress(val), "UTF-8");
  }

  public static void main(String[] args) throws IOException {
    String str = "%5B%7B%22lastUpdateTime%22%3A%222011-10-28+9%3A39%3A41%22%2C%22smsList%22%3A%5B%7B%22liveState%22%3A%221";
    System.out.println("原长度：" + str.length());
    System.out.println("压缩后字符串：" + GZIPUtils.compress(str).toString().length());
    // System.out.println("解压缩后字符串：" +
    // StringUtils.newStringUtf8(GZIPUtils.uncompress(GZIPUtils.compress(str))));
    System.out.println("解压缩后字符串：" + GZIPUtils.uncompress(GZIPUtils.compress(str)));
  }
}
