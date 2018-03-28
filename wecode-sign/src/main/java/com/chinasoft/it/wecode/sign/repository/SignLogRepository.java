package com.chinasoft.it.wecode.sign.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chinasoft.it.wecode.sign.domain.SignLog;

@Repository
public interface SignLogRepository extends JpaRepository<SignLog, String> {

	
}
