package com.cs.it.wecode.web.advice;

import com.cs.it.wecode.dto.error.ErrorDTO;
import com.cs.it.wecode.dto.error.ReturnErrorsDTO;
import com.cs.it.wecode.exception.WeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 统一异常处理类
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class RestExceptionAdvice {

    /**
     * 通用异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ReturnErrorsDTO> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        ReturnErrorsDTO singleton = ReturnErrorsDTO.singleton(ex.getMessage());
        return new ResponseEntity<>(singleton, HttpStatus.resolve(singleton.getHttpStatus()));
    }

    // 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常，详情继续往下看代码
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ReturnErrorsDTO> handleBindException(BindException ex) {
        log.error(ex.getMessage(), ex);
        String collect = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        ReturnErrorsDTO singleton = ReturnErrorsDTO.singleton(collect);
        return new ResponseEntity<>(singleton, HttpStatus.resolve(singleton.getHttpStatus()));
    }

    //处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ReturnErrorsDTO> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error(ex.getMessage(), ex);
        String message = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        ReturnErrorsDTO singleton = ReturnErrorsDTO.singleton(message);
        return new ResponseEntity<>(singleton, HttpStatus.resolve(singleton.getHttpStatus()));
    }

    // 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ReturnErrorsDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        String message = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        ReturnErrorsDTO singleton = ReturnErrorsDTO.singleton(message);
        return new ResponseEntity<>(singleton, HttpStatus.resolve(singleton.getHttpStatus()));
    }

    @ExceptionHandler(WeException.class)
    public ResponseEntity<ReturnErrorsDTO> handleWeException(WeException ex) {
        log.error(ex.getMessage(), ex);
        ReturnErrorsDTO errors = ex.getErrors();
        return new ResponseEntity<>(errors, HttpStatus.resolve(errors.getHttpStatus()));
    }
}
