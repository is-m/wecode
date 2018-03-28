package com.chinasoft.it.wecode.sign.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chinasoft.it.wecode.sign.domain.Sign;

@Repository
public interface SignRepository extends JpaRepository<Sign, String> {

	/**
	 * 根据用户和日期返回打卡记录
	 * 
	 * @param userId
	 * @param dayStart
	 * @param dayEnd
	 * @return
	 */
	Sign findOneByUserIdAndSignDate(String userId, Date date);

}
