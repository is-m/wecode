package com.chinasoft.it.wecode.todo.vo;

import lombok.Data;

@Data
public class MyTaskVO {

    /**
     * ID
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 申请人
     */
    private String applicant;

    /**
     * 参与人
     */
    private String participate;

    /**
     * 任务链接
     */
    private String url;

    /**
     * 待处理，已完成
     */
    private String status;

    /**
     * 业务主键
     */
    private String businessKey;

    /**
     * 业务类型
     */
    private String businessType;


}
