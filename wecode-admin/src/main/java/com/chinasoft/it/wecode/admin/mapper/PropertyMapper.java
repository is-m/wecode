package com.chinasoft.it.wecode.admin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.chinasoft.it.wecode.admin.domain.Property;
import com.chinasoft.it.wecode.admin.dto.PropertyDto;
import com.chinasoft.it.wecode.admin.dto.PropertyResultDto;
import com.chinasoft.it.wecode.common.mapper.BaseMapper;

@Mapper(componentModel = "spring")
public interface PropertyMapper extends BaseMapper<Property, PropertyDto, PropertyResultDto> {

	PropertyMapper INSTANCE = Mappers.getMapper(PropertyMapper.class);

}
