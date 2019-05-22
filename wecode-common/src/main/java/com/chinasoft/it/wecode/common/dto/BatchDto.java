package com.chinasoft.it.wecode.common.dto;

import java.util.List;

/**
 * 批量操作Dto
 */
public class BatchDto<T> {

    /**
     * 待删除列表
     */
    private List<T> ds;

    /**
     * 插入列表
     */
    private List<T> ins;

    /**
     * 更新列表
     */
    private List<T> ups;


}
