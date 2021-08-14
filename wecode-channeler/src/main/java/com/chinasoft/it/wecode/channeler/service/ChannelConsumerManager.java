package com.chinasoft.it.wecode.channeler.service;

/**
 * 通道消费管理器
 */
public interface ChannelConsumerManager {

    /**
     *
     * @param data
     */
    void consume(Object data);

    /**
     *
     */
    void shutdown();

}
