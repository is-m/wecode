package com.chinasoft.it.wecode.admin.mapper;

import com.chinasoft.it.wecode.admin.domain.RichText;
import com.chinasoft.it.wecode.admin.dto.RichTextCreateDto;
import com.chinasoft.it.wecode.admin.dto.RichTextDto;
import com.chinasoft.it.wecode.admin.dto.RichTextUpdateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RichTextMapper {

    RichText toEntity(RichTextCreateDto dto);

    RichText toEntity(RichTextUpdateDto dto);

    RichTextDto toDto(RichText entity);

}
