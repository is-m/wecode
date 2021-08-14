package com.cs.it.wecode.base.dto.returns;

import com.cs.it.wecode.base.dto.BaseDTO;
import com.cs.it.wecode.base.dto.MetaDTO;
import lombok.Data;

import java.util.List;

/**
 * 集合DTO
 *
 * @param <T>
 */
@Data
public class ReturnCollectionDTO<T> extends BaseDTO {

    private List<T> data;

    private MetaDTO meta;

}
