package com.cs.it.wecode.base.po;

import lombok.Data;

import java.util.Date;

/**
 * 基础 PO
 */
@Data
public class BasePO {

    /**
     * id
     */
    private String id;

    /**
     * 版本号
     */
    private int version;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 最后修改人
     */
    private String updateBy;

    /**
     * 最后修改时间
     */
    private Date updatedDate;

}
