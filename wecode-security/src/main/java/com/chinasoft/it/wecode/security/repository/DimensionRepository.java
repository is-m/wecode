package com.chinasoft.it.wecode.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chinasoft.it.wecode.security.domain.Dimension;

public interface DimensionRepository extends JpaRepository<Dimension, String> {

}
