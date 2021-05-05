package com.cs.it.wecode.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class ReturnErrorsDTO {

    /**
     * 异常请求状态码,未手动指定时取第一个errors的异常状态码
     */
    private int httpStatus;

    /**
     *
     */
    private List<ErrorDTO> errors;

    /**
     * 单异常 500
     *
     * @param message
     * @return
     */
    public static ReturnErrorsDTO singleton(String message) {
        return new ReturnErrorsDTO(500, Collections.singletonList(ErrorDTO.internal(message)));
    }

}
