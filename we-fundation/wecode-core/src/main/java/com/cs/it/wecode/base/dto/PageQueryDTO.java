package com.cs.it.wecode.base.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class PageQueryDTO {

    @Min(1)
    private int page;

    @Min(1)
    @Max(500)
    private int size;

}
