package com.chinasoft.it.wecode;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.chinasoft.it.wecode.asset.domain.Asset;
import com.chinasoft.it.wecode.asset.service.AssetService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=App.class,webEnvironment=WebEnvironment.DEFINED_PORT)
public class AssetServiceTests {

	@Autowired
	private AssetService service;

	@Test
	public void testSave() {
		Asset beforeSaveAsset = new Asset();
		beforeSaveAsset.setName("测试资产-" + System.currentTimeMillis());
		Asset afterSaveAsset = service.save(beforeSaveAsset);
		assertNotNull(afterSaveAsset);
	}

}
