package com.chinasoft.base.config.domain;

/**
 * Registry Rule / 注册表规则
 * 
 */
public class RegistryRule {

    /**
     * id
     */
    private String id;

    /**
     * name
     */
    private String name;
    
    /**
     * description / 说明
     */
    private String desc;

    /**
     * argument string / 参数 ，JSON String
     */
    private String args;

    /**
     * error message / 异常消息
     */
    private String msg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    
}
