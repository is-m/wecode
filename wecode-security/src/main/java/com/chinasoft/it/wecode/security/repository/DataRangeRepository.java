package com.chinasoft.it.wecode.security.repository;

import com.chinasoft.it.wecode.security.domain.DataRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRangeRepository extends JpaRepository<DataRange,String> {
}
