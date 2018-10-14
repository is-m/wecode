package com.chinasoft.it.wecode.security.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.chinasoft.it.wecode.common.util.LogUtils;
import com.chinasoft.it.wecode.common.util.MapUtils;
import com.chinasoft.it.wecode.excel.service.IExcelDataConsumer;
import com.chinasoft.it.wecode.excel.service.IExcelDataProvider;
import com.chinasoft.it.wecode.security.dto.UserExportDto;
import com.chinasoft.it.wecode.security.dto.UserResultDto;

/**
 * 该示例仅作为小量数据演示，超级大数据需要自己实现（看看是否需要原生API），应为可能涉及到分页取数据等操作
 * 
 * @author Administrator
 *
 */
@Service("excel.security.user")
public class UserExcelService implements IExcelDataProvider<UserExportDto>, IExcelDataConsumer {

  private static final Logger logger = LogUtils.getLogger();

  @Autowired
  private UserService service;

  private static final String KEY_USERS = "users";

  @Override
  public Map<String, Object> beansMap() {
    // 此处的key 需要跟
    return MapUtils.newMap(KEY_USERS, new ArrayList<UserResultDto>());
  }

  @SuppressWarnings("unchecked")
  @Override
  public void consume(Map<String, Object> data) throws Exception {
    logger.info("consume excel data for context {}", data);
    List<UserResultDto> users = (List<UserResultDto>) data.get(KEY_USERS);
    service.save(users);
    logger.info("consume excel data successful.");
  }

  @Override
  public Map<String, Object> produce(UserExportDto condition) throws Exception {
    List<UserResultDto> users = null;

    String ids = condition.getIds();
    if (!StringUtils.isEmpty(ids)) {
      users = service.findAll(Arrays.asList(ids.split(",")));
    } else {
      users = service.findPagedData(PageRequest.of(0, Integer.MAX_VALUE), condition).getContent();
    }

    return MapUtils.newMap("users", users);
  }

}
