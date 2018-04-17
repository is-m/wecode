package com.chinasoft.it.wecode.sign.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chinasoft.it.wecode.sign.domain.Catelog;
 

@Repository
public interface CatelogRepository extends JpaRepository<Catelog, String> {

	List<Catelog> findByNameLike(String name);

	List<Catelog> findByStatusOrderBySeqAsc(Integer status);

	
}
