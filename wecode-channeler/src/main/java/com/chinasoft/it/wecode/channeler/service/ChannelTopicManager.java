package com.chinasoft.it.wecode.channeler.service;

public interface ChannelTopicManager {

    /**
     * 主题启动
     * @param topicId
     */
    void start(long topicId);

    /**
     * 主题停止
     * @param topicId
     */
    void stop(long topicId);

    /**
     * 主题重启
     * @param topicId
     */
    default void restart(long topicId){
        stop(topicId);
        start(topicId);
    }

}
