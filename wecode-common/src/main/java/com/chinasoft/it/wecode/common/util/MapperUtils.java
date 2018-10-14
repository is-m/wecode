package com.chinasoft.it.wecode.common.util;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;

import com.chinasoft.it.wecode.common.mapper.BaseMapper;

public class MapperUtils {

  @SuppressWarnings("unused")
  private static final Logger logger = LogUtils.getLogger();

  public static <E, D, R> List<R> from(Collection<E> entities, BaseMapper<E, D, R> mapper) {
    return CollectionUtils.forEach2(entities, item -> mapper.from(item));
  }
}
