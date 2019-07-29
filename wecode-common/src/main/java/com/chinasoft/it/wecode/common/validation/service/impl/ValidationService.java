package com.chinasoft.it.wecode.common.validation.service.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Constraint;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.slf4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.chinasoft.it.wecode.common.constant.CacheConstants;
import com.chinasoft.it.wecode.common.util.ClassUtil;
import com.chinasoft.it.wecode.common.util.LogUtils;
import com.chinasoft.it.wecode.common.util.MapUtils;
import com.chinasoft.it.wecode.common.util.ReflectionUtil;

@Service
public class ValidationService {

  private static Logger log = LogUtils.getLogger();

  private static final String GROUP_DEFAULT = "Default";

  private static final Map<String, Object> TARGET_MAP = MapUtils.newMap(
          "userDto", "com.chinasoft.it.wecode.security.dto.UserDto",
          "richTextCreateDto","com.chinasoft.it.wecode.admin.dto.RichTextCreateDto");

  /**
   * 获取前台验证映射
   * @param target 校验对象，可以是短名（需要进行映射配置），也可以是完整类路径
   * @param group 校验组，为空时只加载为default
   */
  @Cacheable(value = CacheConstants.CACHE_SYS_SUPPORT, key = "'$SYS_VALIDATION_FRONT_' + #p1 + #p0")
  public List<FieldValidationBean> getFrontValidationMapper(String target, String group) {
    List<FieldValidationBean> result = new ArrayList<>();
    // TODO 查找是否存在短命映射
    String fullName = target;
    if(TARGET_MAP.containsKey(target)) {
      fullName = String.valueOf(TARGET_MAP.get(target));
    }
    
    // 检查是否存在类以及校验注解
    Class<?> clz = ClassUtil.forName(fullName);
    Assert.notNull(clz, "Front Validation Mapper not found by target " + target);

    if (StringUtils.isBlank(group)) {
      group = GROUP_DEFAULT;
    }

    List<Field> declaredFields = ReflectionUtil.getAllDeclaredFields(clz, null);
    for (Field field : declaredFields) {
      FieldValidationBean fieldValidationBean = resolveFieldValidation(field, group);
      if (fieldValidationBean != null && fieldValidationBean.hasValidation()) {
        result.add(fieldValidationBean);
      }
    }

    return result;
  }

  private FieldValidationBean resolveFieldValidation(Field field, String group) {
    Objects.requireNonNull(field, "field cannot be null or empty");
    // TODO:待定义注解来获取自定义字段名
    FieldValidationBean result = FieldValidationBean.of(field.getName());
    // 获取所有注解
    Annotation[] annotations = field.getAnnotations();
    // 检查是否校验器相关注解
    for (Annotation item : annotations) {
      // 如果不是校验器相关注解则不进行相关处理
      Class<? extends Annotation> annotationType = item.annotationType();
      if (!(annotationType.isAnnotationPresent(Constraint.class))) {
        continue;
      }

      if (item instanceof NotBlank) {
        NotBlank notBlank = (NotBlank) item;
        if (hasGroup(notBlank.groups(), group)) {
          result.validation("notBlank", null, notBlank.message(), 1);
        }
      } else if (item instanceof Length) {
        Length length = (Length) item;
        if (hasGroup(length.groups(), group)) {
          result.validation("length", MapUtils.newMap("min", length.min(), "max", length.max()), length.message(), 5);
        }
      }else if(item instanceof Range) {
        Range range = (Range)item;
        if (hasGroup(range.groups(), group)) {
          result.validation("length", MapUtils.newMap("min", range.min(), "max", range.max()), range.message(), 5);
        }
      }
    }

    // 将校验集合按优先级排序
    Collections.sort(result.getValidations(), (a, b) -> Integer.compare(a.getPriority(), b.getPriority()));

    return result;
  }


  private boolean hasGroup(Class<?>[] groups, String group) {
    // 如果属性上有默认的校验规则，所有分组校验均需要进行校验
    if (groups == null || groups.length == 0 || (groups.length == 1 && groups[0] == Default.class)) {
      return true;
    }

    for (Class<?> groupClz : groups) {
      if (groupClz == Default.class || Objects.equals(groupClz.getSimpleName(), group)) {
        return true;
      }

    }
    return false;
  }
}
