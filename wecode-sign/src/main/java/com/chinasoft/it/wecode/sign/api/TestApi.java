package com.chinasoft.it.wecode.sign.api;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chinasoft.it.wecode.common.util.GZIPUtils;
import com.chinasoft.it.wecode.common.util.JSONUtils;
import com.chinasoft.it.wecode.common.util.LogUtils;
import com.chinasoft.it.wecode.common.util.StringUtil;
import com.chinasoft.it.wecode.sign.service.impl.CatelogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/test")
public class TestApi {

  private static final Logger log = LogUtils.getLogger();
  
  @Autowired
  private CatelogService catelogService;

  /**
   * TODO 该接口后续要迁移,并且内容可能需要异步处理
   * @throws IOException 
   */
  @PostMapping("/analysis")
  @ResponseStatus(code = HttpStatus.ACCEPTED)
  public void analysis(@RequestBody String compressData) throws IOException {
    // https://blog.csdn.net/beflyabot/article/details/78053130 重复读request.reader or inputstream
    String params = new String(GZIPUtils.uncompressURI(compressData));
    if (!StringUtil.isEmpty(params)) {
      // 因为前台对参数进行了 url 编码,在此进行解码
      params = URLDecoder.decode(params, "utf-8");
      // 将解码后的参数转换为 json 对象
      @SuppressWarnings("unchecked")
      Map<String, Object> map = JSONUtils.getObj(params, Map.class);
      List<?> obj = (List<?>) map.get("data");
      // TODO 待放入异步消息队列
      log.info("data analysis size：{} , content {}", obj.size(), map);
    } else {
      log.warn("data analysis empty");
    }
  }



  @GetMapping("/environment")
  public String env(HttpServletRequest req, HttpServletResponse resp) throws JsonProcessingException {
    ObjectMapper om = new ObjectMapper();
    Map<String, Object> workspace = new HashMap<>();
    Map<String, Object> userMap = new HashMap<>();
    userMap.put("name", "Bob");
    userMap.put("role", "ADMIN");
    workspace.put("user", userMap);
    workspace.put("menus", catelogService.findMenuList());
    String json = om.writeValueAsString(workspace);
    return "workspace=" + json;
    /*
     * for(Cookie cook : req.getCookies()) { if("authentication".equals(cook.getName())) { Cookie
     * cookie = new Cookie("authentication",null); cookie.setMaxAge(0); cookie.setPath("/");
     * resp.addCookie(cookie); ObjectMapper om = new ObjectMapper(); Map<String,Object> workspace =
     * new HashMap<>(); Map<String,Object> userMap = new HashMap<>(); userMap.put("name", "Bob");
     * userMap.put("role", "ADMIN"); workspace.put("user", userMap); workspace.put("menus",
     * catelogService.findMenuList()); String json = om.writeValueAsString(workspace); return
     * "workspace="+json; } } Cookie cookie = new Cookie("authentication","123");//创建新cookie
     * cookie.setMaxAge(100);// 10秒 cookie.setPath("/");//设置作用域
     * resp.addCookie(cookie);//将cookie添加到response的cookie数组中返回给客户端 return null;
     */
  }

  public class Workspace {
    private Map<String, String> user = new HashMap<>();

    private List<Map<String, Object>> menus = new ArrayList<>();

    public Map<String, String> getUser() {
      return user;
    }

    public void setUser(Map<String, String> user) {
      this.user = user;
    }

    public List<Map<String, Object>> getMenus() {
      return menus;
    }

    public void setMenus(List<Map<String, Object>> menus) {
      this.menus = menus;
    }

    public Workspace createTest() {
      Workspace space = new Workspace();
      space.user.put("name", "zhangsan");
      space.user.put("role", "admin");

      Map<String, Object> menu1 = new HashMap<>();
      menu1.put("name", "栏目管理");
      menu1.put("icon", "user");
      menu1.put("path", "/sitemap");


      Map<String, Object> menu11 = new HashMap<>();
      menu11.put("name", "栏目列表");
      menu11.put("icon", "user");
      menu11.put("path", "/List");

      Map<String, Object> menu12 = new HashMap<>();
      menu12.put("name", "栏目维护");
      menu12.put("icon", "user");
      menu12.put("path", "/Edit");

      Map<String, Object> menu121 = new HashMap<>();
      menu121.put("name", "新增栏目");
      menu121.put("icon", "user");
      menu121.put("path", "/add");

      Map<String, Object> menu122 = new HashMap<>();
      menu122.put("name", "更新栏目");
      menu122.put("icon", "user");
      menu122.put("path", "/update");

      menu12.put("children", Arrays.asList(menu121, menu122));

      menu1.put("children", Arrays.asList(menu11, menu12));
      space.menus.add(menu1);
      return space;
    }
  }
}
