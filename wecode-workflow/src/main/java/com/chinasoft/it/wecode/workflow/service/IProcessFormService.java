package com.chinasoft.it.wecode.workflow.service;

/**
 * 流程表单服务
 */
public interface IProcessFormService {

    /**
     * 获取最后部署表单
     *
     * @param resourceId resourceId
     * @return 获取流程表单
     */
    Object getProcessForm(String lastDeployId, String resourceId);

    /**
     * 获取流程实例表单
     *
     * @param processInstanceId processInstanceId
     * @param resourceId resourceId
     * @return 获取流程实例表单
     */
    Object getProcessInstanceForm(String processInstanceId, String resourceId);
}
