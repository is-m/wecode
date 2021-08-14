package com.cs.it.wecode.base.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDTO {

    /**
     * 异常代码
     */
    private String code;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 消息内容占位符替换值
     */
    private Object[] replacements;

    /**
     * 异常点
     */
    private ErrorPointer pointer;

    /**
     * 内部异常 500
     *
     * @return
     */
    public static ErrorDTO internal(String message, Object... replacements) {
        return new ErrorDTO("500", "Server Internal Error", message, replacements, null);
    }

    /**
     * 资源未找到 404
     *
     * @return
     */
    public static ErrorDTO notFound(String id) {
        return new ErrorDTO("404", "Resource not found", "the resource '" + id + "' not found", null, null);
    }

    /**
     * 参数错误 400
     *
     * @return
     */
    public static ErrorDTO param(String source, String pointer, String message, Object... replacements) {
        return new ErrorDTO("400", "Bad Request", message, replacements, new ErrorPointer(source, pointer));
    }

}
