package com.chinasoft.it.wecode.security.service;

import java.util.HashSet;
import java.util.Set;

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
import com.chinasoft.it.wecode.security.dto.PermissionDto;
import com.chinasoft.it.wecode.security.dto.PermissionResultDto;
import com.chinasoft.it.wecode.security.dto.RoleDto;
import com.chinasoft.it.wecode.security.dto.RoleResultDto;
import com.chinasoft.it.wecode.security.service.impl.RoleService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecurityTestApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleServiceTest {

	@Autowired
	private RoleService roleService;
	
	@Test
	public void test01CreateRole() {
		long currentTimes = System.currentTimeMillis();
		RoleDto dto = new RoleDto();
		dto.setName("ADMIN " +currentTimes);
		dto.setCode(currentTimes+"");
		Set<PermissionResultDto> permissions = new HashSet<>();
		PermissionResultDto permissionResultDto = new PermissionResultDto();
		permissionResultDto.setCode(currentTimes+"");
		permissionResultDto.setNote("test child");
		permissions.add(permissionResultDto);
		dto.setPermissions(permissions);
		roleService.create(dto);
		
		Page<RoleResultDto> findPagedList = roleService.findPagedList(new PageRequest(1,10), null);
		System.out.println(findPagedList);
	}
}
