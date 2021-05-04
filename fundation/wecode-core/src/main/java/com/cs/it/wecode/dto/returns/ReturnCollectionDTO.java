package com.cs.it.wecode.dto.returns;

import com.cs.it.wecode.dto.BaseDTO;
import com.cs.it.wecode.dto.MetaDTO;
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
