package com.cs.it.wecode.base.po;

/**
 * 删除历史记录 PO，记录所有删除的记录，相当于对删除的数据进行归档，不再使用删除或者状态字段共存
 */
public class DeleteHistoryPO extends BasePO {

    /**
     *
     */
    private String table;

    private String record;

}
