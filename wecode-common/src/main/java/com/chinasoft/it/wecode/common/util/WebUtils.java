package com.chinasoft.it.wecode.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.chinasoft.it.wecode.common.exception.NoImplementedException;

public class WebUtils {

  /**
   * 获取分页参数
   * 
   * @param request
   * @return
   */
  public static Pageable getPageable(HttpServletRequest request) {
    switch (PageConstants.PARAM_HTTP_TYPE_DEFAULT) {
      case PageConstants.PARAM_HTTP_TYPE_QUERY:
        String page = request.getParameter(PageConstants.PARAM_PAGE);
        int curPage = NumberUtils.tryParse(page, 0);
        String size = request.getParameter(PageConstants.PARAM_SIZE);
        int pageSize = NumberUtils.tryParse(size, PageConstants.DEFAULT_SIZE);

        if (PageConstants.START_PAGE == 1 && curPage > 0) {
          curPage--;
        }

        Sort sort = getSort(request);
        return PageRequest.of(curPage, pageSize, sort);
      default:
        throw new NoImplementedException();
    }
  }

  private static Sort getSort(HttpServletRequest request) {
    String[] sorts = request.getParameterValues(SortConstant.PARAM_SORT);
    if (sorts != null && sorts.length > 0) {
      List<Order> orders = new ArrayList<>();
      for (String sortString : sorts) {
        String[] splited = sortString.split(SortConstant.PARAM_SPLITTER);
        if (splited.length > 2)
          throw new IllegalArgumentException("request sort condition parse faild,sortString=" + sortString);
        String direction = splited.length > 1 ? splited[1] : SortConstant.DIRECTION_DEFAULT;
        orders.add(new Order(Direction.fromString(direction), splited[0]));
      }
      return Sort.by(orders);
    }
    return Sort.unsorted();
  }

  /**
   * 根据名字获取cookie
   * @param request
   * @param name cookie名字
   * @return
   */
  public Cookie getCookieByName(HttpServletRequest request, String name) {
    Map<String, Cookie> cookieMap = ReadCookieMap(request);
    if (cookieMap.containsKey(name)) {
      Cookie cookie = (Cookie) cookieMap.get(name);
      return cookie;
    } else {
      return null;
    }
  }

  public void addCookie(HttpServletResponse resp, String name, String value) {
    Cookie cookie = new Cookie(name.trim(), value.trim());
    cookie.setMaxAge(30 * 60);// 设置为30min
    cookie.setPath("/");
    cookie.setDomain("");
    resp.addCookie(cookie);
  }

  /**
   * 修改cookie
   * @param request
   * @param response
   * @param name
   * @param value
   * 注意一、修改、删除Cookie时，新建的Cookie除value、maxAge之外的所有属性，例如name、path、domain等，都要与原Cookie完全一样。否则，浏览器将视为两个不同的Cookie不予覆盖，导致修改、删除失败。
   */
  public void editCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
    Cookie[] cookies = request.getCookies();
    if (null == cookies) {
      System.out.println("没有cookie==============");
    } else {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(name)) {
          System.out.println("原值为:" + cookie.getValue());
          cookie.setValue(value);
          cookie.setPath("/");
          cookie.setMaxAge(30 * 60);// 设置为30min
          System.out.println("被修改的cookie名字为:" + cookie.getName() + ",新值为:" + cookie.getValue());
          response.addCookie(cookie);
          break;
        }
      }
    }

  }

  /**
   * 删除cookie
   * @param request
   * @param response
   * @param name
   */
  public void delCookie(HttpServletRequest request, HttpServletResponse response, String name) {
    Cookie[] cookies = request.getCookies();
    if (null == cookies) {
      System.out.println("没有cookie==============");
    } else {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(name)) {
          cookie.setValue(null);
          cookie.setMaxAge(0);// 立即销毁cookie
          cookie.setPath("/");
          System.out.println("被删除的cookie名字为:" + cookie.getName());
          response.addCookie(cookie);
          break;
        }
      }
    }
  }


  /**
   * 将cookie封装到Map里面
   * @param request
   * @return
   */
  private Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
    Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
    Cookie[] cookies = request.getCookies();
    if (null != cookies) {
      for (Cookie cookie : cookies) {
        cookieMap.put(cookie.getName(), cookie);
      }
    }
    return cookieMap;
  }

}
