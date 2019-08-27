package com.chinasoft.it.wecode.admin.dto;

import com.chinasoft.it.wecode.common.service.Query;

public class RichTextQueryDto {

    /**
     * 分类
     */
    private String type;

    /**
     * 关键字
     */
    @Query(type = Query.Type.LKI,field = "name,title,content")
    private String keyword;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
