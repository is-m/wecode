package com.cs.it.wecode.base.dto;

import lombok.Data;

import java.util.HashMap;

/**
 * 元数据DTO
 */
@Data
public class MetaDTO extends HashMap<String, Object> {

    /**
     * 状态，通常1表示成功，0或者其他值表示失败
     */
    public void setStatus(String status) {
        this.put("status", status);
    }

    public String getStatus() {
        return (String) this.get("status");
    }

}
