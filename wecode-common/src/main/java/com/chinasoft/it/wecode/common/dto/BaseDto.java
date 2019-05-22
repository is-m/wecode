package com.chinasoft.it.wecode.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 基础Dto
 *
 * 备注：Dto的编写原则，是不应该知道有领域对象存在的
 */
public class BaseDto implements Serializable {

    private static final long serialVersionUID = -164700840565676697L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
