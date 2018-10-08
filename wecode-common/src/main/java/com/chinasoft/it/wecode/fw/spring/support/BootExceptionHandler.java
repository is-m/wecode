package com.chinasoft.it.wecode.fw.spring.support;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.chinasoft.it.wecode.bean.ApiResponse;
import com.chinasoft.it.wecode.exception.HttpCodeProvider;

@ControllerAdvice
public class BootExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(BootExceptionHandler.class);

  /**
   *  拦截Exception类的异常
   * @param e
   * @return
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse> exception(Exception e) {
    int status = 500;
    if (e instanceof HttpCodeProvider) {
      status = ((HttpCodeProvider) e).getCode();
    }
    log.error(e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.valueOf(status)).body(new ApiResponse(String.valueOf(status), ExceptionUtils.getMessage(e)));
  }

}
