package com.chinasoft.it.wecode.workflow.vo;

import lombok.Data;

@Data
public class ProcessFormVO {
    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 流程定义ID
     */
    private String processDefineId;

    /**
     * 流程部署ID
     */
    private String processDeployId;

    /**
     * 表单定义
     */
    private Object formDefine;

    /**
     * 表单值
     */
    private Object formValue;
}
