package com.chinasoft.it.wecode.common.dto;

import com.chinasoft.it.wecode.common.util.CollectionUtils;

import java.util.List;

/**
 * 批量操作Dto
 */
public class BatchDto<T> extends BaseDto {

    /**
     * 待删除列表
     */
    private List<String> dels;

    /**
     * 插入列表
     */
    private List<T> adds;

    /**
     * 更新列表
     */
    private List<T> upds;

    public List<String> getDels() {
        return dels;
    }

    public void setDels(List<String> dels) {
        this.dels = dels;
    }

    public List<T> getAdds() {
        return adds;
    }

    public void setAdds(List<T> adds) {
        this.adds = adds;
    }

    public List<T> getUpds() {
        return upds;
    }

    public void setUpds(List<T> upds) {
        this.upds = upds;
    }

    public boolean isEmpty() {
        return CollectionUtils.isAllEmpty(dels, adds, upds);
    }

    public boolean isEmptyDels() {
        return dels == null || dels.isEmpty();
    }

    public boolean isEmptyAdds() {
        return adds == null || adds.isEmpty();
    }

    public boolean isEmptyUpds() {
        return upds == null || upds.isEmpty();
    }
}
