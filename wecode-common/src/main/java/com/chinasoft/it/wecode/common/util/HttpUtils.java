package com.chinasoft.it.wecode.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

/**
 * Http请求工具类
 * https://www.cnblogs.com/shijiaqi1066/p/3753224.html
 * @author Administrator
 * 
 */
public class HttpUtils {

  private static final Logger log = LogUtils.getLogger();

  /**
   * 设置连接主机超时（单位：毫秒） 
   */
  private static final int TIMEOUT_SOCKET = 30000;

  /**
   * 设置从主机读取数据超时（单位：毫秒）  
   */
  private static final int TIMEOUT_READ = 30000;

  /**
   * 预设置
   */
  private static void preSetting(URLConnection conn) {
    // 设置请求超时
    conn.setConnectTimeout(TIMEOUT_SOCKET);
    conn.setReadTimeout(TIMEOUT_READ);

    // 设置通用的请求属性
    conn.setRequestProperty("accept", "*/*");
    conn.setRequestProperty("connection", "Keep-Alive");
    conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
  }

  /**
   * 向指定URL发送GET方法的请求
   * 
   * @param url
   *            发送请求的URL
   * @return URL 所代表远程资源的响应结果
   */
  public static String get(String url) {
    String result = "";
    BufferedReader in = null;
    try {
      URL realUrl = new URL(url);
      // 打开和URL之间的连接
      URLConnection connection = realUrl.openConnection();
      preSetting(connection);

      // 建立实际的连接
      connection.connect();
      // 获取所有响应头字段
      Map<String, List<String>> map = connection.getHeaderFields();
      // 遍历所有的响应头字段
      for (String key : map.keySet()) {
        System.out.println(key + "--->" + map.get(key));
      }
      // 定义 BufferedReader输入流来读取URL的响应
      in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line;
      while ((line = in.readLine()) != null) {
        result += line;
      }
    } catch (Exception e) {
      log.error("发送 POST 请求出现异常！" + e, e);
    } finally {
      StreamUtils.close(in);
    }
    return result;
  }

  /**
   * 向指定 URL 发送POST方法的请求
   * 
   * @param url
   *            发送请求的 URL
   * @param param
   *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
   * @return 所代表远程资源的响应结果
   */
  public static String post(String url, String param) {
    PrintWriter out = null;
    BufferedReader in = null;
    String result = "";
    try {
      URL realUrl = new URL(url);
      // 打开和URL之间的连接
      URLConnection conn = realUrl.openConnection();
      preSetting(conn);

      // 设置是否从httpUrlConnection读入，默认情况下是true;
      conn.setDoOutput(true);
      conn.setDoInput(true);
      // Post 请求不能使用缓存
      conn.setUseCaches(false);
      // 获取URLConnection对象对应的输出流
      out = new PrintWriter(conn.getOutputStream());
      // 发送请求参数
      out.print(param);
      // flush输出流的缓冲
      out.flush();
      // 定义BufferedReader输入流来读取URL的响应
      in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

      String line;
      while ((line = in.readLine()) != null) {
        result += line;
      }
      StreamUtils.copyToString(conn.getInputStream());
    } catch (Exception e) {
      log.error("发送 POST 请求出现异常！" + e, e);
    } finally {
      StreamUtils.close(in, out);
    }
    return result;
  }

}
