package com.chinasoft.it.wecode.common.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据重写Dto
 *
 * 注意：使用该对象的接口表示接口内部内涉及的数据会被该对象内的数据重写（先清理后写入新的），所以如果对清理的数据的ID敏感的话，建议先评估后再考虑是否要使用该对象
 * @param <T>
 */
public class RewritableDto<T>  {

    /**
     * 重写的主记录ID，服务会根据该ID进行数据清理
     *
     * 例如：要重置用户权限时，这里放的应该为主对象用户的ID
     */
    private String id;

    /**
     * 待重写入的数据
     */
    private List<T> data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
