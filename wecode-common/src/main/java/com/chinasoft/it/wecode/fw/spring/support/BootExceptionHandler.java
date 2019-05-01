package com.chinasoft.it.wecode.fw.spring.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.chinasoft.it.wecode.common.dto.ApiResponse;
import com.chinasoft.it.wecode.common.util.LogUtils;
import com.chinasoft.it.wecode.exception.HttpCodeProvider;

/**
 * 异常处理规范，尽量的少返回数据给调用方
 * @author Administrator
 *
 */
@ControllerAdvice
@ResponseBody
public class BootExceptionHandler {

  private static final Logger log = LogUtils.getLogger();

  // FIXME:生成环境时，这里尽量配置false ，不然会将堆栈返回给前台
  @Value("${app.exception.responseStackTrace:true}")
  private boolean responseStackTrace;

  // 通用异常解析
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse> exception(Exception e) {
    int status = 500;
    if (e instanceof HttpCodeProvider) {
      status = ((HttpCodeProvider) e).getCode();
    }
    
    if(status < 500) {
      log.warn(e.getMessage());
    }else {
      log.error(e.getMessage(), e);
    }
    return ResponseEntity.status(HttpStatus.valueOf(status))
        .body(ApiResponse.of(status, e.getMessage(), responseStackTrace ? null : ExceptionUtils.getStackTrace(e)));
  }

  // 参数静态校验异常
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiResponse constraintViolationException(ConstraintViolationException e) {
    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
    Map<String, Object> result = new HashMap<>();
    for (ConstraintViolation<?> violation : violations) {
      result.put(violation.getPropertyPath().toString(), violation.getMessage());
    }
    return ApiResponse.of(40, "非法参数 illegal argument", result);
  }

  // 函数内参数异常
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiResponse illegalArgumentException(IllegalArgumentException e) {
    return ApiResponse.of(41, e.getMessage(), responseStackTrace ? null : ExceptionUtils.getStackTrace(e));
  }

}
