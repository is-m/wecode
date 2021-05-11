package com.cs.it.wecode.base.dto.returns;

import com.cs.it.wecode.base.dto.BaseDTO;
import com.cs.it.wecode.base.dto.MetaDTO;
import lombok.Data;

/**
 * 对象或者值返回DTO
 *
 * @param <T>
 */
@Data
public class ReturnSingletonDTO<T> extends BaseDTO {

    private T data;

    private MetaDTO meta;

}
