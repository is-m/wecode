package com.chinasoft.it.wecode.common.util;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Servlet 工具类
 * @author Administrator
 *
 */
public class ServletUtils {

  /**
   * 获取访问用户的IP
   * @param request 请求对象
   * @return 请求者的IP
   */
  public static String getRemoteAddr(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
  }

  /**
   * 获取请求头
   * @param req
   * @param exclustions
   * @return
   */
  public static Map<String, String> getHeader(HttpServletRequest req, String... exclustions) {
    Map<String, String> result = new HashMap<>();
    List<String> exclustionList = Arrays.asList(exclustions == null ? new String[0] : exclustions);
    Enumeration<String> headerNames = req.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String name = headerNames.nextElement();
      if (!exclustionList.contains(name)) {
        result.put(name, req.getHeader(name));
      }
    }
    return result;
  }

}
