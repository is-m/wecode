package com.chinasoft.it.wecode.workflow.service;

import com.chinasoft.it.wecode.workflow.vo.ProcessModelVO;

/**
 * 模型服务
 */
public interface IProcessModelService {

    /**
     * 创建模型
     */
    void createModel(ProcessModelVO processModelVO);

    /**
     * 修改模型
     */
    void updateModel(ProcessModelVO processModelVO);

    /**
     * 删除模型
     */
    void deleteModel(String modelId);

    /**
     *
     */
    void startModel(String modelId);

    /**
     * 模型停用
     */
    void stopModel(String modelId);
}
