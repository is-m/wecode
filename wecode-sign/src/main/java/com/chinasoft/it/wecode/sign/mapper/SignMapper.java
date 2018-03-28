package com.chinasoft.it.wecode.sign.mapper;

import org.mapstruct.Mapper;

import com.chinasoft.it.wecode.common.mapper.BaseMapper;
import com.chinasoft.it.wecode.sign.domain.Sign;
import com.chinasoft.it.wecode.sign.dto.SignDto;
import com.chinasoft.it.wecode.sign.dto.SignResultDto;

@Mapper(componentModel = "spring")
public interface SignMapper extends BaseMapper<Sign, SignDto, SignResultDto> {

}
