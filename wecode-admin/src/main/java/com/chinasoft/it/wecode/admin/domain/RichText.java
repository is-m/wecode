package com.chinasoft.it.wecode.admin.domain;

import com.chinasoft.it.wecode.base.BaseAuditableEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 富文本
 */
@Entity
@Table(name = "wc_rich_text")
public class RichText extends BaseAuditableEntity {

    /**
     * 类型
     */
    private String type;

    /**
     * 名称
     */
    private String name;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
