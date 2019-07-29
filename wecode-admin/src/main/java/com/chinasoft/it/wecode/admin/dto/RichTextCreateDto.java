package com.chinasoft.it.wecode.admin.dto;

import com.chinasoft.it.wecode.common.dto.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


public class RichTextCreateDto extends BaseDto {

    @ApiModelProperty(name = "type", notes = "类型", example = "html")
    @NotBlank
    @Length(max = 10)
    private String type;

    @ApiModelProperty(name = "name", notes = "名称", example = "test")
    @NotBlank
    @Length(max = 100)
    private String name;

    @ApiModelProperty(name = "name", notes = "标题", example = "这是一个测试标题")
    @Length(max = 2000)
    private String title;

    @ApiModelProperty(name = "name", notes = "内容", example = "这是一个测试内容")
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
