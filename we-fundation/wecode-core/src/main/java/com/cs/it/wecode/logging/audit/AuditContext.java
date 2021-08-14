package com.cs.it.wecode.logging.audit;

import lombok.Data;

@Data
public class AuditContext {

    // 审计上下文本身的参数

    /**
     * 请求参数
     */
    private Object[] arguments;

    /**
     * 请求结果
     */
    private Object result;


}
