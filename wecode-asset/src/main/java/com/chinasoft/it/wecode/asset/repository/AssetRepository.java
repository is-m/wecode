package com.chinasoft.it.wecode.asset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chinasoft.it.wecode.asset.domain.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, String> {

	
}
