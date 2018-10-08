package com.chinasoft.it.wecode.common.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.springframework.util.StreamUtils.*;
import org.slf4j.Logger;

public class StreamUtils {

  private static final Logger log = LogUtils.getLogger();

  public static void close(Closeable... closeables) {
    if (closeables != null) {
      for (Closeable closeable : closeables) {
        if (closeable != null) {
          try {
            closeable.close();
          } catch (IOException e) {
            log.warn("io close faild {}", e.getMessage());
          } catch (Throwable e) {
            log.warn("io close faild {}", e.getMessage());
          }
        }
      }
    }
  }

  public static String copyToString(InputStream in) {
    try {
      return org.springframework.util.StreamUtils.copyToString(in, Charset.forName("utf-8"));
    } catch (IOException e) {
      return "";
    }
  }
}
