package com.cs.it.wecode.dto.returns;

import com.cs.it.wecode.dto.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 分页返回结果
 *
 * @param <T>
 */
@Data
public class ReturnPagedCollectionDTO<T> extends BaseDTO {

    /**
     * data
     */
    private List<T> data;

    /**
     * 元数据
     */
    private PagedMetaDTO meta = PagedMetaDTO.empty();

}
