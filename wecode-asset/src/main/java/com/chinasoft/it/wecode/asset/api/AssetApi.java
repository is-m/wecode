package com.chinasoft.it.wecode.asset.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinasoft.it.wecode.asset.domain.Asset;
import com.chinasoft.it.wecode.asset.service.AssetService;

@RestController
@RequestMapping("/asset")
public class AssetApi {

	@Autowired
	private AssetService assetService;

	@PostMapping
	public Asset create(@RequestBody Asset asset) {
		return assetService.save(asset);
	}

}
