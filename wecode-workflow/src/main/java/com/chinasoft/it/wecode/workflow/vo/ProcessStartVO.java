package com.chinasoft.it.wecode.workflow.vo;

import lombok.Data;

import java.util.Map;

@Data
public class ProcessStartVO {

    /**
     * 流程ID，未提供时自动生成
     */
    private String id;

    /**
     * 申请人，未提供时默认当前登录用户
     */
    private String applicant;

    /**
     * 启动时间,提供时默认当前系统时间
     */
    private String startTime;

    /**
     * 表单
     */
    private Map<String,Object> dynamicForms;

}
