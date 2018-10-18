package com.chinasoft.it.wecode.security.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
import org.springframework.transaction.annotation.Transactional;

import com.chinasoft.it.wecode.SecurityTestApp;
import com.chinasoft.it.wecode.security.dto.PermissionDto;
import com.chinasoft.it.wecode.security.dto.PermissionResultDto;
import com.chinasoft.it.wecode.security.dto.RoleResultDto;
import com.chinasoft.it.wecode.security.service.impl.PermissionService;
import com.chinasoft.it.wecode.security.util.SecurityConstant;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecurityTestApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PermissionServiceTest {

  @Autowired
  private PermissionService service;

  @Test
  @Transactional
  public void test01CreatePermission() {
    String currentTimes = System.currentTimeMillis() + "";
    PermissionDto permissionDto = new PermissionDto();
    permissionDto.setCode("SYS::Test::test01CreatePermission" + currentTimes);
    permissionDto.setNote("测试创建权限点");
    permissionDto.setType(SecurityConstant.PERMISSION_TYPE_MODULE);

    HashSet<RoleResultDto> roles = new HashSet<>();
    RoleResultDto admin = new RoleResultDto();
    admin.setCode(currentTimes);
    admin.setName("ADMIN");
    roles.add(admin);
    PermissionResultDto create = service.create(permissionDto);
    System.out.println(create);

    permissionDto = new PermissionDto();
    permissionDto.setCode("SYS::Test::test01CreatePermission" + System.currentTimeMillis());
    permissionDto.setNote("测试创建权限点2");
    permissionDto.setType(SecurityConstant.PERMISSION_TYPE_MODULE);
    create = service.create(permissionDto);
    System.out.println(create);

    permissionDto = new PermissionDto();
    permissionDto.setCode("1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111SYS::Test::test01CreatePermission"
        + System.currentTimeMillis());
    permissionDto.setNote("测试创建权限点3");
    permissionDto.setType(SecurityConstant.PERMISSION_TYPE_MODULE);
    permissionDto.setPid(
        "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
    create = service.create(permissionDto);
    System.out.println(create);
  }

  /**
   * TODO:待通知在控制事物的函数中使用parallelStream时，仅可能只做静态数据的处理（也就是不要操作其他线程的数据）
   */
  @Test
  @Transactional
  public void test01PattralCreatePermission() {
    String currentTimes = System.currentTimeMillis() + "";
    List<PermissionDto> waitCreateList = new ArrayList<>();
    for (int i = 1; i < 11; i++) {
      PermissionDto dto = new PermissionDto();
      dto.setCode("SYS::Test::test01CreatePermission" + System.currentTimeMillis() + "" + i);
      dto.setNote("测试创建权限点2");
      dto.setType(SecurityConstant.PERMISSION_TYPE_MODULE);
      if (i % 5 == 0) {
        dto.setPid(currentTimes);
      }
      waitCreateList.add(dto);
    }
    /*
     * waitCreateList.parallelStream().forEach(item->{ service.create(item); });
     */
    // service.save(waitCreateList);
  }

  @Test
  public void test02FindPagedData() {
    Page<PermissionResultDto> findPagedList = service.findPagedList(PageRequest.of(0, 10), null);
    System.out.println(findPagedList);
  }
}
