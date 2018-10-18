package com.chinasoft.it.wecode.common.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import org.slf4j.Logger;

import com.chinasoft.it.wecode.common.exception.ValidationException;
import com.chinasoft.it.wecode.common.util.CollectionUtils;
import com.chinasoft.it.wecode.common.util.LogUtils;

/**
 * 
 * @see org.springframework.validation.beanvalidation.BeanValidationPostProcessor
 * @author Administrator
 *
 */
public class ValidationProcessor {

  @SuppressWarnings("unused")
  private static final Logger logger = LogUtils.getLogger();

  public static Validator getValidator() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    return validator;
  }

  public static void valid(Object waitFor) throws ValidationException {
    valid(waitFor, Default.class);
  }

  public static void valid(Object waitFor, Class<?>... groups) throws ValidationException {
    /*if (waitFor == null) {
      throw new NullPointerException("待校验的对象不能为空");
    }*/

    Set<ConstraintViolation<Object>> result = getValidator().validate(waitFor, groups);
    if (CollectionUtils.notEmpty(result)) {
      StringBuilder errorBuilder = new StringBuilder();
      for (ConstraintViolation<Object> item : result) {
        errorBuilder.append("对象属性:").append(item.getPropertyPath()).append(",国际化key:").append(item.getMessageTemplate()).append(",错误信息:")
            .append(item.getMessage()).append(";");
      }
      throw new ValidationException(errorBuilder.toString());
    }
  }


}
