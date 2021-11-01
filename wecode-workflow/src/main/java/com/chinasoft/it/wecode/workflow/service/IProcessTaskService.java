package com.chinasoft.it.wecode.workflow.service;

public interface IProcessTaskService {

    /**
     * 转审批
     * @return
     */
    Object transfer();

    /**
     * 暂停
     * @return
     */
    Object stop();

    /**
     * 恢复
     * @return
     */
    Object resume();

}
