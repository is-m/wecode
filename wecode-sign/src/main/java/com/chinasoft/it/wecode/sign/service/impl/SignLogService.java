package com.chinasoft.it.wecode.sign.service.impl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasoft.it.wecode.sign.dto.SignLogDto;
import com.chinasoft.it.wecode.sign.dto.SignLogResultDto;
import com.chinasoft.it.wecode.sign.mapper.SignLogMapper;
import com.chinasoft.it.wecode.sign.repository.SignLogRepository;

@Service
public class SignLogService {

	@Autowired
	private SignLogRepository repo;

	@Autowired
	private SignLogMapper mapper;

	/**
	 * create sign log
	 * 
	 * @param dto
	 * @return
	 */
	public SignLogResultDto create(@Valid SignLogDto dto) {
		return mapper.from(repo.save(mapper.to(dto)));
	}
}
