package com.chinasoft.it.wecode.asset.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasoft.it.wecode.asset.domain.Asset;
import com.chinasoft.it.wecode.asset.repository.AssetRepository;

/**
 * asset service
 * @author Administrator
 *
 */
@Service
public class AssetService {

	@Autowired
	private AssetRepository repo;

	/**
	 * save
	 * @param asset
	 * @return
	 */
	public Asset save(Asset asset) {
		return repo.save(asset);
	}

	/**
	 * find all
	 * @return
	 */
	public List<Asset> findAll() {
		return repo.findAll();
	}
}
