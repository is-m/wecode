package com.cs.it.wecode.base.dto.returns;

import com.cs.it.wecode.base.dto.MetaDTO;

public class PagedMetaDTO extends MetaDTO {

    public static final long DEFAULT_PAGE = 1;

    public static final long DEFAULT_SIZE = 15;

    /**
     * 总页数
     *
     * @return
     */
    public long getTotalPages() {
        return (long) this.getOrDefault("totalPages", 0);
    }

    /**
     * 总记录数
     *
     * @return
     */
    public long getTotalElements() {
        return (long) this.getOrDefault("totalElements", 0);
    }

    /**
     * 当前页
     *
     * @return
     */
    public long getPage() {
        return (long) this.getOrDefault("page", DEFAULT_PAGE);
    }

    /**
     * 页大小
     *
     * @return
     */
    public long getSize() {
        return (long) this.getOrDefault("size", DEFAULT_SIZE);
    }

    public static PagedMetaDTO of(long page, long size, long totalElements) {
        PagedMetaDTO result = new PagedMetaDTO();
        result.put("page", page);
        result.put("size", size);
        result.put("totalElements", totalElements);
        result.put("totalPages", (totalElements + size - 1) / size);
        return result;
    }

    public static PagedMetaDTO empty() {
        return of(DEFAULT_PAGE, DEFAULT_SIZE, 0);
    }
}
