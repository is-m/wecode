package com.chinasoft.it.wecode.ratelimit.vo;

import java.util.List;

public class LimitConfigVO {

    /**
     * 统一资源定位，默认为api定位
     */
    private String url;

    /**
     * 默认时间窗
     */
    private String defaultWindowCron;

    /**
     * 限制时间窗
     */
    private List<TimeWindowVO> windows;

    /**
     * 默认周期内次数
     */
    private long defaultLimitCount;

    /**
     * 默认周期，单位：秒
     */
    private long defaultLimitInterval;

    /**
     * 自定义访问次数限制
     */
    private List<LimitVO> limits;

}
