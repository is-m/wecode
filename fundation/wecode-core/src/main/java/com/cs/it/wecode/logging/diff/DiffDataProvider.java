package com.cs.it.wecode.logging.diff;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 差异数据提供接口
 */
public interface DiffDataProvider {

    /**
     * 获取差异对象
     *
     * @param objectId 对象ID
     * @return Serializable 可序列化的值
     */
    Serializable getDiffObject(String objectId);

    /**
     * 批量获取差异对象(适用于批量处理接口优化)
     *
     * @param objectIdList objectIdList
     * @return List<Serializable>
     */
    default List<Serializable> getDiffObjects(List<String> objectIdList) {
        return objectIdList == null ? null : objectIdList.stream().map(this::getDiffObject).collect(Collectors.toList());
    }

}
