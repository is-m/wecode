package com.cs.it.wecode.dto.returns;

import com.cs.it.wecode.dto.BaseDTO;
import com.cs.it.wecode.dto.MetaDTO;
import lombok.Data;

/**
 * 对象或者值返回DTO
 *
 * @param <T>
 */
@Data
public class ReturnSingleDTO<T> extends BaseDTO {

    private T data;

    private MetaDTO meta;

}
