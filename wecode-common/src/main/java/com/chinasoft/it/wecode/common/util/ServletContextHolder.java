package com.chinasoft.it.wecode.common.util;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chinasoft.it.wecode.common.exception.NoImplementedException;

public class ServletContextHolder {

  private static final ThreadLocal<ServletContext> $ = new ThreadLocal<>();

  public void init(HttpServletRequest req, HttpServletResponse resp) {
    $.set(new ServletContext(req, resp));
  }

  public void remove() {
    $.remove();
  }


  public static HttpServletRequest getRequest() {
    ServletContext sc = $.get();
    return sc != null ? sc.getRequest() : null;
  }

  public static HttpServletResponse getResponse() {
    ServletContext sc = $.get();
    return sc != null ? sc.getResponse() : null;
  }

  public static ISession getSession() {
    HttpSession session = getRequest().getSession(false);
    return session != null ? new HttpUserSession(session) : null;
  }

  public static class ServletContext {

    private HttpServletRequest request;

    private HttpServletResponse response;

    public HttpServletRequest getRequest() {
      return request;
    }

    public void setRequest(HttpServletRequest request) {
      this.request = request;
    }

    public HttpServletResponse getResponse() {
      return response;
    }

    public void setResponse(HttpServletResponse response) {
      this.response = response;
    }

    public ServletContext(HttpServletRequest request, HttpServletResponse response) {
      this.request = request;
      this.response = response;
    }

    public ServletContext(HttpServletRequest request) {
      this.request = request;
    }

  }


  public interface ISession extends Map<String, Object> {

  }

  public static class HttpUserSession implements ISession {

    private HttpSession session;

    private Set<String> keys;

    public HttpUserSession(HttpSession session) {
      Objects.requireNonNull(session);
      this.session = session;
      keys = new ConcurrentSkipListSet<>();
      Enumeration<String> attributeNames = session.getAttributeNames();
      while (attributeNames.hasMoreElements()) {
        keys.add(attributeNames.nextElement());
      }
    }

    @Override
    public int size() {
      return keys.size();
    }

    @Override
    public boolean isEmpty() {
      return keys.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
      return keys.contains(key);
    }

    @Override
    public Object get(Object key) {
      return session.getAttribute(String.valueOf(key));
    }

    @Override
    public Object put(String key, Object value) {
      Object oldValue = session.getAttribute(key);
      session.setAttribute(key, value);
      keys.add(key);
      return oldValue;
    }

    @Override
    public Object remove(Object key) {
      String skey = String.valueOf(key);
      Object oldValue = session.getAttribute(skey);
      session.removeAttribute(skey);
      keys.remove(skey);
      return oldValue;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Object> m) {
      for (Entry<? extends String, ? extends Object> item : m.entrySet()) {
        put(item.getKey(), item.getValue());
      }
    }

    @Override
    public void clear() {
      for (String key : keys) {
        session.removeAttribute(key);
      }
      keys.clear();
    }

    @Override
    public Set<String> keySet() {
      return keys;
    }

    // un support collection about collection value operations
    @Override
    public Collection<Object> values() {
      throw new NoImplementedException();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
      throw new NoImplementedException();
    }

    @Override
    public boolean containsValue(Object value) {
      // for (Object key : keys) {
      // Object attribute = session.getAttribute(String.valueOf(key));
      // if(Objects.equals(value, attribute)) return true; } return false;
      throw new NoImplementedException();
    }

  }
}
