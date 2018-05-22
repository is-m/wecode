package com.chinasoft.it.wecode.security.service;

import java.util.HashSet;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chinasoft.it.wecode.SecurityTestApp;
import com.chinasoft.it.wecode.security.domain.Role;
import com.chinasoft.it.wecode.security.dto.PermissionDto;
import com.chinasoft.it.wecode.security.dto.PermissionResultDto;
import com.chinasoft.it.wecode.security.dto.RoleDto;
import com.chinasoft.it.wecode.security.dto.RoleResultDto;
import com.chinasoft.it.wecode.security.service.impl.PermissionService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecurityTestApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PermissionServiceTest {

	@Autowired
	private PermissionService service;

	@Test
	public void test01CreatePermission() {
		String currentTimes = System.currentTimeMillis()+"";
		PermissionDto permissionDto = new PermissionDto();
		permissionDto.setCode("SYS::Test::test01CreatePermission" + currentTimes);
		permissionDto.setNote("测试创建权限点");
		
		HashSet<RoleResultDto> roles = new HashSet<>();
		RoleResultDto admin = new RoleResultDto();
		admin.setCode(currentTimes);
		admin.setName("ADMIN");
		roles.add(admin);
		permissionDto.setRoles(roles);
		PermissionResultDto create = service.create(permissionDto);
		System.out.println(create);
	}

	@Test
	public void test02FindPagedData() {
		Page<PermissionResultDto> findPagedList = service.findPagedList(new PageRequest(0, 10), null);
		System.out.println(findPagedList);
	}
}
