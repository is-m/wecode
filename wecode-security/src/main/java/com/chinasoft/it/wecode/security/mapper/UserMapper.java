package com.chinasoft.it.wecode.security.mapper;

import org.mapstruct.Mapper;

import com.chinasoft.it.wecode.common.mapper.BaseMapper;
import com.chinasoft.it.wecode.security.domain.User;
import com.chinasoft.it.wecode.security.dto.UserDto;
import com.chinasoft.it.wecode.security.dto.UserResultDto;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserDto, UserResultDto> {

}
