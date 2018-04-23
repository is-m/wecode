package com.chinasoft.it.wecode.security.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.chinasoft.it.wecode.base.BaseService;
import com.chinasoft.it.wecode.common.mapper.BaseMapper;
import com.chinasoft.it.wecode.security.domain.User;
import com.chinasoft.it.wecode.security.dto.UserDto;
import com.chinasoft.it.wecode.security.dto.UserResultDto;

@Service
public class UserService extends BaseService<User, UserDto, UserResultDto> {

	public UserService(JpaRepository<User, String> repository, BaseMapper<User, UserDto, UserResultDto> mapper) {
		super(repository, mapper);
	}

}
