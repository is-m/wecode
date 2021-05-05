package com.cs.it.wecode.dto.returns;

import com.cs.it.wecode.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReturnMessageDTO extends BaseDTO {

    /**
     * 消息编码
     */
    private String status;

    /**
     * 消息
     */
    private String message;

    /**
     * 请求成功
     *
     * @return
     */
    public static ReturnMessageDTO ok() {
        return ok("success");
    }

    /**
     * 请求成功
     *
     * @param message
     * @return
     */
    public static ReturnMessageDTO ok(String message) {
        return normal("info", message);
    }

    /**
     * 请求失败
     *
     * @return
     */
    public static ReturnMessageDTO fail() {
        return fail("request has problem");
    }

    /**
     * 请求失败
     *
     * @param message
     * @return
     */
    public static ReturnMessageDTO fail(String message) {
        return normal("error", message);
    }

    /**
     * 警告
     *
     * @param message message
     * @return ReturnMessageDTO
     */
    public static ReturnMessageDTO warn(String message) {
        return normal("warn", message);
    }

    private static ReturnMessageDTO normal(String status, String message) {
        return new ReturnMessageDTO(status, message);
    }
}
