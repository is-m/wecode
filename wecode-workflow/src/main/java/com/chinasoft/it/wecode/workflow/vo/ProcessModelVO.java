package com.chinasoft.it.wecode.workflow.vo;

import lombok.Data;

/**
 * 流程模型VO
 */
@Data
public class ProcessModelVO {

    /**
     * ID
     */
    private String id;

    /**
     * 模型名称
     */
    private String name;

    /**
     * 最后定义ID
     */
    private String lastDefineId;

    /**
     * 最后部署ID
     */
    private String lastDeployId;

    /**
     * init 未启用，enable 已启用，disable 已停用
     */
    private String status;


}
