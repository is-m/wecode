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
    private String lastUpdateBy;

    /**
     * 最后修改时间
     */
    private Date lastUpdatedDate;

    /**
     * 版本号
     */
    private int version;
}
