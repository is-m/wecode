package com.chinasoft.it.wecode.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ReflectionUtil {

  /**
   * 获取类所有的Field属性
   * 
   * @param clz
   * @param filter
   * @return
   */
  public static List<Field> getAllDeclaredFields(Class<?> clz, Function<Field, Boolean> filter) {
    Objects.requireNonNull(clz, "clz cannot be null");

    Field[] declaredFields = clz.getDeclaredFields();
    // 预留五个位置，降低父类属性导致集合扩容
    List<Field> result = new ArrayList<>(declaredFields.length + 5);
    for (Field field : declaredFields) {
      if (filter == null || Boolean.TRUE.equals(filter.apply(field))) {
        result.add(field);
      }
    }

    // 往获取父类中的字段
    Class<?> currentClz = clz;
    while ((currentClz = currentClz.getSuperclass()) != null) {
      declaredFields = currentClz.getDeclaredFields();
      for (Field field : declaredFields) {
        if (filter == null || Boolean.TRUE.equals(filter.apply(field))) {
          result.add(field);
        }
      }
    }

    return result;
  }
}
