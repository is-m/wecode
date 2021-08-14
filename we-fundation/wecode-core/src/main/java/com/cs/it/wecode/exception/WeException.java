package com.cs.it.wecode.exception;

import com.cs.it.wecode.base.dto.error.ReturnErrorsDTO;

/**
 * 异常
 */
public class WeException extends RuntimeException {

    private ReturnErrorsDTO errors;

    public WeException(ReturnErrorsDTO errors) {
        this.errors = errors;
    }

    public WeException(String message) {
        super(message);
        this.errors = ReturnErrorsDTO.singleton(message);
    }

    public WeException(String message, Throwable cause) {
        super(message, cause);
        this.errors = ReturnErrorsDTO.singleton(message);
    }

    public WeException(Throwable cause) {
        super(cause);
        this.errors = ReturnErrorsDTO.singleton(cause.getMessage());
    }

    public WeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errors = ReturnErrorsDTO.singleton(cause.getMessage());
    }

    public ReturnErrorsDTO getErrors() {
        return errors;
    }

    public void setErrors(ReturnErrorsDTO errors) {
        this.errors = errors;
    }
}
