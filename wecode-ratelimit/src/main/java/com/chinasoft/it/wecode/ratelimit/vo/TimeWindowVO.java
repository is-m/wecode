package com.chinasoft.it.wecode.ratelimit.vo;

import java.util.List;

public class TimeWindowVO {

    /**
     * 时间窗表达式，例如 * * 8-23,0-5 * * ? 表示每天8:00:00 - 23:59:59, 0:00:00 - 5:59:59
     */
    private String cron;

    /**
     * 适用用户
     */
    private List<String> users;

}
