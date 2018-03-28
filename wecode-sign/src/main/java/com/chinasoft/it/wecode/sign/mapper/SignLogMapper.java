package com.chinasoft.it.wecode.sign.mapper;

import org.mapstruct.Mapper;

import com.chinasoft.it.wecode.common.mapper.BaseMapper;
import com.chinasoft.it.wecode.sign.domain.SignLog;
import com.chinasoft.it.wecode.sign.dto.SignLogDto;
import com.chinasoft.it.wecode.sign.dto.SignLogResultDto;

@Mapper(componentModel = "spring")
public interface SignLogMapper extends BaseMapper<SignLog, SignLogDto, SignLogResultDto> {

}
