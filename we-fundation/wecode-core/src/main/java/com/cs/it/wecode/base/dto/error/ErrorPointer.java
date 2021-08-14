package com.cs.it.wecode.base.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 异常点
 */
@Data
@AllArgsConstructor
public class ErrorPointer {

    /**
     * 参数名称，例如：arg
     */
    private String source;

    /**
     * 实际异常参数路径，例如：arg/attr/name
     */
    private String pointer;

}
