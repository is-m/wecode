package com.chinasoft.it.wecode.workflow.service;

import com.chinasoft.it.wecode.workflow.vo.ProcessCompleteVO;
import com.chinasoft.it.wecode.workflow.vo.ProcessStartVO;

/**
 * 流程实例服务
 */
public interface IProcessInstanceService {

    Object start(ProcessStartVO processStartVO);

    Object complete(ProcessCompleteVO processCompleteVO);

    Object stop(String processInstanceId);

    Object resume(String processInstanceId);

    Object delete(String processInstanceId);

    Object detail(String processInstanceId);
}
