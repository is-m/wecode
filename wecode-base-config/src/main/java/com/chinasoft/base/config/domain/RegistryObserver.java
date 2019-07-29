package com.chinasoft.base.config.domain;

/**
 * Registry Observer / 注册表观察者
 * 
 * @author Administrator
 *
 */
public class RegistryObserver {

    private String id;
    
    /**
     * is before / 是否在之前触发，是改变之后触发
     */
    private boolean isBefore;
    
    /**
     * event type / 事件类型 ，内部接口，REST API
     */
    private String type;
    
    /**
     * 事件值，例如内部接口时，写 xxxService.hello('xxx',1);
     */
    private String value;
    
    /**
     * 说明
     */
    private String desc;
}
