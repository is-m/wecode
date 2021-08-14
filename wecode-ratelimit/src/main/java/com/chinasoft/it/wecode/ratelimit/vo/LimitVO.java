package com.chinasoft.it.wecode.ratelimit.vo;

/**
 * 流量限制配置
 */
public class LimitVO {

    /**
     * 时间周期内最大调用次数
     */
    private long count;

    /**
     * 时间周期(单位秒)
     */
    private String interval;

    /**
     * 受控用户
     */
    private String[] users;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String[] getUsers() {
        return users;
    }

    public void setUsers(String[] users) {
        this.users = users;
    }
}
